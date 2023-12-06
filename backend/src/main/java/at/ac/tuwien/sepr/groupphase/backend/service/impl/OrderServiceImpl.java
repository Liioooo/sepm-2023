package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final TicketMapper ticketMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, TicketMapper ticketMapper, UserService userService) {
        this.orderRepository = orderRepository;
        this.ticketMapper = ticketMapper;
        this.userService = userService;
    }

    @Override
    public void createOrder(OrderCreateDto orderCreateDto) {
        var user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        var tickets = orderCreateDto.getTickets().stream().map(ticketMapper::createTicketDtoToTicket).toList();

        var order = Order.builder()
            .tickets(tickets)
            .orderDate(OffsetDateTime.now())
            .user(user)
            .build();

        orderRepository.save(order);
    }
}
