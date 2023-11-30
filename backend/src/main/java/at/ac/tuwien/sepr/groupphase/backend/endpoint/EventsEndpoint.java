package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventsEndpoint {

    private final EventService eventService;

    public EventsEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    @PermitAll
    @GetMapping()
    @Operation(summary = "Get events, optionally filter by search criteria")
    public List<EventListDto> getEvents(EventSearchDto search) {
        return eventService.getEventsBySearch(search);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @Operation(summary = "Creates a new event")
    public void createEvent(@ModelAttribute EventCreateDto eventCreateDto) {
        eventService.createEvent(eventCreateDto);
        // Just for testing file upload...
    }
}
