package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderUpdateTicketsDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RedeemReservationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface OrderService {

    /**
     * Queries an order by its id.
     *
     * @param id the id of the order
     * @return the order
     */
    Order getOrderById(Long id);

    /**
     * Queries all orders of the current user.
     */
    List<Order> getOrdersOfCurrentUser();

    /**
     * Creates a new order.
     *
     * @param orderCreateDto the order to create
     */
    void createOrder(OrderCreateDto orderCreateDto);

    /**
     * Redeems a reservation.
     *
     * @param orderId              the id of the order to redeem
     * @param redeemReservationDto the tickets to redeem in the reservation
     */
    void redeemReservation(Long orderId, RedeemReservationDto redeemReservationDto);

    /**
     * Deletes a reservation.
     *
     * @param orderId the id of the order to delete
     */
    void deleteReservation(Long orderId);

    /**
     * Update the tickets of an order in terms of which Tickets belong to the order.
     * This allows to cancel tickets.
     *
     * @param orderId               the id of the Order
     * @param orderUpdateTicketsDto The updated selection of tickets of the order
     * @param currentUser           The currently authenticated user, must not be null
     * @return The updated order
     * @throws NotFoundException if the order of orderUpdateTicketsDto is not found in the database or currentUser does not own the order
     */
    Order updateOrderTickets(Long orderId, OrderUpdateTicketsDto orderUpdateTicketsDto, @NotNull ApplicationUser currentUser);
}
