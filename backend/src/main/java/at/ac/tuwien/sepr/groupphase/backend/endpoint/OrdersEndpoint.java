package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrdersEndpoint {

    private final OrderService orderService;

    public OrdersEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @Secured("ROLE_USER")
    @PostMapping()
    @Operation(summary = "Books a new order")
    @Transactional
    public void createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        try {
            orderService.createOrder(orderCreateDto);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The order could not be created. Maybe one or more tickets have already been purchased by another party.", e);
        }
    }
}
