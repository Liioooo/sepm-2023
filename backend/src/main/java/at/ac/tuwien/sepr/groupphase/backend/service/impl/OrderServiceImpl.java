package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TicketMapper ticketMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            TicketRepository ticketRepository,
                            TicketMapper ticketMapper,
                            OrderMapper orderMapper,
                            UserService userService
    ) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.orderMapper = orderMapper;
        this.userService = userService;
    }

    @Override
    public void createOrder(OrderCreateDto orderCreateDto) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));

        var order = orderRepository.save(orderMapper.orderCreateDtoToOrder(orderCreateDto, user));

        var tickets = Arrays.stream(orderCreateDto.getTickets())
            .map(ticketMapper::createTicketDtoToTicket)
            .peek(ticket -> ticket.setOrder(order))
            .toList();

        ticketRepository.saveAll(tickets);
    }
}
