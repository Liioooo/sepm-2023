package at.ac.tuwien.sepr.groupphase.backend.unittest.service;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.OrderDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.TicketDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderUpdateTicketsDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketOrderUpdateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles({"test", "generateTestData"})
@DirtiesContext(classMode = AFTER_CLASS)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ApplicationUserDataGenerator applicationUserDataGenerator;

    @Autowired
    private OrderDataGenerator orderDataGenerator;

    @Autowired
    private TicketDataGenerator ticketDataGenerator;

    private ApplicationUser user;
    private Order order;

    @BeforeEach
    void init() {
        initUser();
        initOrder();
        mockBeans();
    }

    void mockBeans() {
        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        for (Order order : orderDataGenerator.getTestData()) {
            order.setTickets(new ArrayList<>(ticketDataGenerator.getTestData().stream().filter(t -> Objects.equals(t.getOrder().getId(), order.getId())).toList()));
            Mockito.when(orderRepository.findOrderByIdAndUserId(order.getId(), user.getId())).thenReturn(Optional.of(order));
        }
    }

    void initOrder() {
        order = orderDataGenerator.getTestData().get(3);
    }

    void initUser() {
        user = applicationUserDataGenerator.getTestData().get(2);
    }

    @Test
    void updateOrderTickets_withAllOriginalTickets_returnsOrderContainingAllOriginalTickets() {
        var tickets = ticketDataGenerator.getTestData().stream()
            .filter(t -> Objects.equals(t.getOrder().getId(), order.getId()))
            .toList();
        OrderUpdateTicketsDto orderUpdateTicketsDto = new OrderUpdateTicketsDto(tickets.stream().map(t -> new TicketOrderUpdateDto(t.getId())).toList());

        assertThrows(ConflictException.class, () -> {
            orderService.updateOrderTickets(order.getId(), orderUpdateTicketsDto, user);
        });
    }

    @Test
    void updateOrderTickets_withNoTickets_returnsOrderContainingNoTickets() {
        OrderUpdateTicketsDto orderUpdateTicketsDto = new OrderUpdateTicketsDto(List.of());
        var updatedOrder = orderService.updateOrderTickets(order.getId(), orderUpdateTicketsDto, user);

        assertAll(
            () -> assertNotNull(updatedOrder.getTickets()),
            () -> assertThat(updatedOrder.getTickets()).isEmpty()
        );
    }

    @Test
    void updateOrderTickets_withValidTicket_returnsOrderContainingValidTicket() {
        var tickets = ticketDataGenerator.getTestData().stream()
            .filter(t -> Objects.equals(t.getOrder().getId(), order.getId()))
            .toList();
        Ticket remainingTicket = tickets.get(0);
        OrderUpdateTicketsDto orderUpdateTicketsDto = new OrderUpdateTicketsDto(List.of(new TicketOrderUpdateDto(remainingTicket.getId())));
        var updatedOrder = orderService.updateOrderTickets(order.getId(), orderUpdateTicketsDto, user);

        assertAll(
            () -> assertNotNull(updatedOrder.getTickets()),
            () -> assertThat(updatedOrder.getTickets()).isNotEmpty(),
            () -> assertThat(updatedOrder.getTickets())
                .extracting(Ticket::getId, Ticket::getTicketCategory, Ticket::getRowNumber, Ticket::getSeatNumber, ticket -> ticket.getOrder().getId())
                .containsExactly(tuple(remainingTicket.getId(), remainingTicket.getTicketCategory(), remainingTicket.getRowNumber(), remainingTicket.getSeatNumber(), remainingTicket.getOrder().getId()))
        );
    }

    @Test
    void updateOrderTickets_withInvalidTicket_returnsOrderWithEmptyTickets() {
        var tickets = ticketDataGenerator.getTestData().stream()
            .filter(t -> !Objects.equals(t.getOrder().getId(), order.getId()))
            .toList();
        Ticket ticketFromOtherOrder = tickets.get(0);
        OrderUpdateTicketsDto orderUpdateTicketsDto = new OrderUpdateTicketsDto(List.of(new TicketOrderUpdateDto(ticketFromOtherOrder.getId())));
        var updatedOrder = orderService.updateOrderTickets(order.getId(), orderUpdateTicketsDto, user);

        assertAll(
            () -> assertNotNull(updatedOrder.getTickets()),
            () -> assertThat(updatedOrder.getTickets()).isEmpty()
        );
    }

    @Test
    void updateOrderTickets_withSameValidTicketMultipleTimes_returnsOrderContainingValidTicketOnlyOnce() {
        var tickets = ticketDataGenerator.getTestData().stream()
            .filter(t -> Objects.equals(t.getOrder().getId(), order.getId()))
            .toList();
        Ticket remainingTicket = tickets.get(0);
        tickets = new ArrayList<>(List.of(remainingTicket, remainingTicket, remainingTicket));
        OrderUpdateTicketsDto orderUpdateTicketsDto = new OrderUpdateTicketsDto(tickets.stream().map(t -> new TicketOrderUpdateDto(t.getId())).toList());
        var updatedOrder = orderService.updateOrderTickets(order.getId(), orderUpdateTicketsDto, user);

        assertAll(
            () -> assertNotNull(updatedOrder.getTickets()),
            () -> assertThat(updatedOrder.getTickets()).isNotEmpty(),
            () -> assertThat(updatedOrder.getTickets())
                .extracting(Ticket::getId, Ticket::getTicketCategory, Ticket::getRowNumber, Ticket::getSeatNumber, ticket -> ticket.getOrder().getId())
                .containsExactly(tuple(remainingTicket.getId(), remainingTicket.getTicketCategory(), remainingTicket.getRowNumber(), remainingTicket.getSeatNumber(), remainingTicket.getOrder().getId()))
        );
    }
}
