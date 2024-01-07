package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @Transactional
    public ResponseEntity<Object> getLocation(@PathVariable long id, boolean includeHalls) {
        if (includeHalls) {
            return new ResponseEntity<>(this.locationMapper.locationToLocationHallsDto(locationService.getLocationById(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(this.locationMapper.locationToLocationDetailDto(locationService.getLocationById(id)), HttpStatus.OK);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new location")
    public LocationDetailDto createLocation(@RequestBody @Valid LocationCreateDto locationCreateDto) {
        return this.locationMapper.locationToLocationDetailDto(locationService.createLocation(locationCreateDto));
    }
}
