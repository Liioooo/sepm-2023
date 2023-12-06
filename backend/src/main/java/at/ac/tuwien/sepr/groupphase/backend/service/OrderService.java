package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;

public interface OrderService {

    /**
     * Creates a new order.
     *
     * @param orderCreateDto the order to create
     */
    void createOrder(OrderCreateDto orderCreateDto);

}
