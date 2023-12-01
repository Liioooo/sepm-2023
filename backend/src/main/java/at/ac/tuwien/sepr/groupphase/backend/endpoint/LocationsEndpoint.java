package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/locations")
public class LocationsEndpoint {

    private final LocationService locationService;

    private final LocationMapper locationMapper;

    private final PageMapper pageMapper;

    public LocationsEndpoint(LocationService locationService, LocationMapper locationMapper, PageMapper pageMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.pageMapper = pageMapper;
    }

    @PermitAll
    @GetMapping()
    @Operation(summary = "Get locations, optionally filter by search criteria")
    public PageDto<LocationDetailDto> getLocations(LocationSearchDto search, Pageable pageable) {
        return pageMapper.toPageDto(locationService.getLocationsBySearch(search, pageable), this.locationMapper::locationToLocationDetailDto);
    }

    @PermitAll
    @GetMapping("{id}")
    @Operation(summary = "Get a single location by id")
    public LocationDetailDto getLocation(@PathVariable long id) {
        return this.locationMapper.locationToLocationDetailDto(locationService.getLocationById(id));
    }

}
