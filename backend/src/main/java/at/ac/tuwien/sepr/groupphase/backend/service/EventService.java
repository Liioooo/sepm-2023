package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    /**
     * Finds event by search criteria.
     *
     * @param search the search criteria
     * @return the collection of events
     */
    Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable);


    /**
     * Creates a new event.
     *
     * @param eventCreateDto data of the event to create
     */
    void createEvent(EventCreateDto eventCreateDto);
}
