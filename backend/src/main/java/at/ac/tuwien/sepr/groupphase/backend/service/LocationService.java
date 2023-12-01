package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    /**
     * Finds locations by search criteria.
     *
     * @param search the search criteria
     * @return the collection of locations
     */
    PageDto<LocationDetailDto> getLocationsBySearch(LocationSearchDto search, Pageable pageable);

    /**
     * Finds a location by id.
     *
     * @param id of the location to get
     * @return the location
     */
    LocationDetailDto getLocationById(long id);
}
