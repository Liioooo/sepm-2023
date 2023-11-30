package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;

import java.util.List;

public interface LocationService {

    /**
     * Finds locations by search criteria.
     *
     * @param search the search criteria
     * @return the collection of locations
     */
    List<LocationDetailDto> getLocationsBySearch(LocationSearchDto search);

    /**
     * Finds a location by id.
     *
     * @param id of the location to get
     * @return the location
     */
    LocationDetailDto getLocationById(long id);
}
