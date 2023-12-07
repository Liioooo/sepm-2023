package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventsEndpoint {

    private final EventService eventService;

    private final EventMapper eventMapper;

    private final PageMapper pageMapper;

    public EventsEndpoint(EventService eventService, EventMapper eventMapper, PageMapper pageMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.pageMapper = pageMapper;
    }

    @PermitAll
    @GetMapping("{id}")
    @Operation(summary = "Get event by id")
    @Transactional
    public EventDetailDto getEvent(@PathVariable long id) {
        return eventMapper.toEventDetailDto(eventService.getEvent(id));
    }

    @PermitAll
    @GetMapping()
    @Operation(summary = "Get events, optionally filter by search criteria")
    public PageDto<EventListDto> getEvents(EventSearchDto search, Pageable pageable) {
        return this.pageMapper.toPageDtoListMapper(
            eventService.getEventsBySearch(search, pageable), this.eventMapper::eventCollectionToEventListDtoCollection
        );
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @Operation(summary = "Creates a new event")
    public void createEvent(@ModelAttribute EventCreateDto eventCreateDto) {
        eventService.createEvent(eventCreateDto);
        // Just for testing file upload...
    }
}
