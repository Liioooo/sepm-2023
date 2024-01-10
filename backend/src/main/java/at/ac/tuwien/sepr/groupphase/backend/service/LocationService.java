package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface LocationService {

    /**
     * Finds locations by search criteria.
     *
     * @param search the search criteria
     * @return the collection of locations
     */
    Page<Location> getLocationsBySearch(LocationSearchDto search, Pageable pageable);

    /**
     * Finds a location by id.
     *
     * @param id of the location to get
     * @return the location
     */
    Location getLocationById(long id);

    /**
     * Create a new location from a DTO and save it to the DB.
     *
     * @param locationCreateDto the DTO to save
     * @return the newly persisted location
     */
    Location createLocation(LocationCreateDto locationCreateDto);

    /**
     * Finds a hall by id.
     *
     * @param id of the hall to get
     * @return the hall
     */
    Hall getHallById(long id);

    /**
     * Finds all halls.
     *
     * @return all halls
     */
    Collection<Hall> getAllHalls();
}
