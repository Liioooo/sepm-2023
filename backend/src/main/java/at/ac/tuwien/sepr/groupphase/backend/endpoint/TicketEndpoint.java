package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/tickets")
public class TicketEndpoint {

    private final OrderService orderService;
    private final TicketMapper ticketMapper;

    public TicketEndpoint(OrderService orderService, TicketMapper ticketMapper) {
        this.orderService = orderService;
        this.ticketMapper = ticketMapper;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/verify/{uuid}")
    @Operation(summary = "Get a single Ticket")
    public TicketListDto getTicketByUuid(@PathVariable UUID uuid) {
        Ticket ticket = orderService.getTicketByUuid(uuid);
        return ticketMapper.toTicketListDto(ticket);
    }
}
