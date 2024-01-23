package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventTop10SearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.interfaces.EventWithBoughtCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    /**
     * Finds event by search criteria.
     *
     * @param search the search criteria
     * @return the collection of events
     */
    Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable);

    /**
     * Finds event by search criteria. Does not do a global search like "getEventsBySearch"
     *
     * @param search the search criteria
     * @return the collection of events
     */
    Page<Event> getEventsBySearchWithoutGlobalSearch(EventSearchDto search, Pageable pageable);

    /**
     * Creates a new event.
     *
     * @param eventCreateDto data of the event to create
     */
    void createEvent(EventCreateDto eventCreateDto);

    /**
     * Get a single event by id.
     *
     * @param id the id of the event
     * @return the Event entity
     */
    Event getEvent(long id);

    /**
     * Retrieves a list of the top ten events along with the count of tickets bought for each event.
     *
     * @param searchDto The search criteria to filter and sort the events.
     * @return A list of EventWithBoughtCount objects representing the top ten events.
     */
    List<EventWithBoughtCount> getTopTenEvents(EventTop10SearchDto searchDto);

    /**
     * Retrieves information about occupied seats for a specific event.
     *
     * @param id The unique identifier of the event.
     * @return An array of SeatDto objects representing the occupied seats for the specified event.
     */
    SeatDto[] getOccupiedSeats(long id);

    /**
     * Retrieves the count of occupied standing positions for a specific event.
     *
     * @param id The unique identifier of the event.
     * @return The count of occupied standing positions for the specified event.
     */
    Integer getOccupiedStandings(long id);
}
