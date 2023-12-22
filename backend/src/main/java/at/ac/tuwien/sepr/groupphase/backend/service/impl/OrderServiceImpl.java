package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderUpdateTicketsDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RedeemReservationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InternalServerException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EmbeddedFileRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepr.groupphase.backend.service.PdfService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TicketMapper ticketMapper;
    private final OrderMapper orderMapper;
    private final EventRepository eventRepository;

    private final PdfService pdfService;

    private final EmbeddedFileRepository embeddedFileRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            TicketRepository ticketRepository,
                            TicketMapper ticketMapper,
                            OrderMapper orderMapper,
                            UserService userService,
                            EventRepository eventRepository,
                            PdfService pdfService,
                            EmbeddedFileRepository embeddedFileRepository
    ) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.pdfService = pdfService;
        this.embeddedFileRepository = embeddedFileRepository;
    }

    @Override
    @Transactional
    public Order getOrderById(Long id) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        var order = orderRepository.findOrderByIdAndUserId(id, user.getId()).orElseThrow(() -> new NotFoundException("Order not found"));

        // Trigger lazy loading of tickets
        order.getTickets().size();

        return order;
    }

    @Override
    public List<Order> getOrdersOfCurrentUser() {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        return orderRepository.findOrdersByUserId(user.getId());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized void createOrder(OrderCreateDto orderCreateDto) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        var event = eventRepository.findById(orderCreateDto.getEventId()).orElseThrow(() -> new NotFoundException("Event not found"));
        var hall = event.getHall();

        // Check if tickets are still available
        for (var ticket : Arrays.stream(orderCreateDto.getTickets()).filter(t -> t.getTicketCategory() == TicketCategory.SEATING).toList()) {
            if (ticket.getRowNumber() > hall.getRows().size() || ticket.getSeatNumber() > hall.getRows().get((int) (ticket.getRowNumber() - 1)).getNumberOfSeats()) {
                throw new ConflictException("Seat does not exist");
            }

            var existingTickets = ticketRepository.findValidSeatingTicketsByEventIdAndSeatNumberAndRowNumber(
                orderCreateDto.getEventId(), ticket.getSeatNumber(), ticket.getRowNumber()
            );

            if (!existingTickets.isEmpty()) {
                if (existingTickets.size() > 1) {
                    LOGGER.error("Multiple valid tickets found for event id {}, seat number {} and row number {}, this should never happen!",
                        orderCreateDto.getEventId(), ticket.getSeatNumber(), ticket.getRowNumber());
                }

                throw new ConflictException("One ore more tickets have already been sold");
            }
        }

        int standingTicketCount = Arrays.stream(orderCreateDto.getTickets()).filter(t -> t.getTicketCategory() == TicketCategory.STANDING).toList().size();
        int validStandingTickets = ticketRepository.findValidStandingTicketsByEventId(orderCreateDto.getEventId());

        long standingCountInHall = event.getHall().getStandingCount();

        if (standingTicketCount + validStandingTickets > standingCountInHall) {
            throw new ConflictException("There are not enough standing tickets available");
        }

        // Save order and tickets
        var order = orderRepository.saveAndFlush(orderMapper.orderCreateDtoToOrder(orderCreateDto, user));

        var tickets = Arrays.stream(orderCreateDto.getTickets())
            .map(ticketMapper::createTicketDtoToTicket)
            .peek(ticket -> ticket.setOrder(order))
            .toList();

        var dbTickets = ticketRepository.saveAllAndFlush(tickets);

        if (orderCreateDto.getOrderType() == OrderType.BUY) {
            createInvoicePdf(order, dbTickets, event);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized void redeemReservation(Long orderId, RedeemReservationDto redeemReservationDto) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        var order = orderRepository.findOrderByIdAndUserId(orderId, user.getId()).orElseThrow(() -> new NotFoundException("Order not found"));

        if (order.getOrderType() != OrderType.RESERVE) {
            throw new ConflictException("Order is not a reservation");
        }

        if (order.getEvent().getStartDate().isBefore(OffsetDateTime.now().plusMinutes(30))) {
            throw new ConflictException("Reservation has expired");
        }

        List<Ticket> standingTicketsInOrder = order.getTickets().stream().filter(t -> t.getTicketCategory() == TicketCategory.STANDING).toList();
        int standingTicketsToRedeem = Arrays.stream(redeemReservationDto.getTickets()).filter(t -> t.getTicketCategory() == TicketCategory.STANDING).toList().size();

        if (standingTicketsToRedeem > standingTicketsInOrder.size()) {
            throw new ConflictException("Cannot redeem more standing tickets than were reserved");
        }

        List<Ticket> tickets = new ArrayList<>(order.getTickets().stream().filter(t -> {
            if (t.getTicketCategory() == TicketCategory.SEATING) {
                return Arrays.stream(redeemReservationDto.getTickets()).anyMatch(rt -> Objects.equals(rt.getSeatNumber(), t.getSeatNumber()) && Objects.equals(rt.getRowNumber(), t.getRowNumber()));
            }
            return false;
        }).toList());

        tickets.addAll(standingTicketsInOrder.stream().limit(standingTicketsToRedeem).toList());

        order.getTickets().clear();
        order.getTickets().addAll(tickets);
        order.setOrderType(OrderType.BUY);
        order.setOrderDate(OffsetDateTime.now());
        orderRepository.saveAndFlush(order);

        var event = eventRepository.findById(order.getEvent().getId()).orElseThrow(() -> new NotFoundException("Event not found"));
        createInvoicePdf(order, tickets, event);
    }

    @Override
    @Transactional
    public void deleteReservation(Long orderId) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        var order = orderRepository.findOrderByIdAndUserId(orderId, user.getId()).orElseThrow(() -> new NotFoundException("Order not found"));

        if (order.getOrderType() != OrderType.RESERVE) {
            throw new ConflictException("Order is not a reservation");
        }

        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public void updateOrderTickets(Long orderId, OrderUpdateTicketsDto orderUpdateTicketsDto, @NotNull ApplicationUser currentUser) {
        var order = orderRepository.findOrderByIdAndUserId(orderId, currentUser.getId()).orElseThrow(() -> new NotFoundException("Order not found"));

        if (order.getEvent().getEndDate().isBefore(OffsetDateTime.now())) {
            throw new ConflictException("Event is already in the past");
        }

        List<Ticket> tickets = new ArrayList<>(order.getTickets().stream().filter(t -> orderUpdateTicketsDto.getTickets().stream().anyMatch(orderUpdateTicket -> Objects.equals(orderUpdateTicket.getId(), t.getId()))).toList());
        List<Ticket> cancelledTickets = new ArrayList<>(order.getTickets());
        cancelledTickets.removeAll(tickets);

        order.getTickets().clear();
        order.getTickets().addAll(tickets);
        orderRepository.save(order);
        if (order.getOrderType() == OrderType.BUY) {
            this.createCancellationInvoicePdf(order, cancelledTickets, order.getEvent());
        }
    }

    private void createInvoicePdf(Order order, List<Ticket> tickets, Event event) {
        try {
            var pdfFile = pdfService.createInvoicePdf(order, tickets, event);
            order.setReceipt(pdfFile);
            orderRepository.saveAndFlush(order);
            embeddedFileRepository.saveAndFlush(pdfFile);
        } catch (IOException | TemplateException e) {
            throw new InternalServerException("Could not generate invoice PDF.", e);
        }
    }

    private void createCancellationInvoicePdf(Order order, List<Ticket> tickets, Event event) {
        try {
            var pdfFile = pdfService.createCancellationInvoicePdf(order, tickets, event);
            order.getCancellationReceipts().add(pdfFile);
            orderRepository.saveAndFlush(order);
            embeddedFileRepository.saveAndFlush(pdfFile);
        } catch (IOException | TemplateException e) {
            throw new InternalServerException("Could not generate cancellation invoice PDF.", e);
        }
    }
}
