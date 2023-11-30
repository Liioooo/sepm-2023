package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;

import java.util.List;

public interface EventService {

    /**
     * Finds event by search criteria.
     *
     * @param search the search criteria
     * @return the collection of events
     */
    List<EventListDto> getEventsBySearch(EventSearchDto search);


    /**
     * Creates a new event.
     *
     * @param eventCreateDto data of the event to create
     */
    void createEvent(EventCreateDto eventCreateDto);
}
