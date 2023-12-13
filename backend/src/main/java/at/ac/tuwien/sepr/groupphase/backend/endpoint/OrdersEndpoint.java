package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RedeemReservationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrdersEndpoint {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrdersEndpoint(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping("{id}")
    @Operation(summary = "Gets order by id")
    public OrderDetailDto getOrderById(@PathVariable Long id) {
        return orderMapper.orderToOrderDetailDto(orderService.getOrderById(id));
    }

    @Secured("ROLE_USER")
    @GetMapping()
    @Operation(summary = "Queries all orders of the current user")
    public Collection<OrderListDto> getOrdersOfUser() {
        return orderService.getOrdersOfCurrentUser().stream()
            .map(orderMapper::orderToOrderListDto)
            .toList();
    }

    @Secured("ROLE_USER")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Books a new order")
    public void createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        orderService.createOrder(orderCreateDto);
    }

    @Secured("ROLE_USER")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Converts a reservation to an order")
    public void redeemReservation(@PathVariable Long id, @Valid @RequestBody RedeemReservationDto redeemReservationDto) {
        orderService.redeemReservation(id, redeemReservationDto);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a reservation")
    public void deleteReservation(@PathVariable Long id) {
        orderService.deleteReservation(id);
    }
}
