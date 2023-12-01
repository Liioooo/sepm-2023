package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    private final PageMapper pageMapper;

    LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, PageMapper pageMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public PageDto<LocationDetailDto> getLocationsBySearch(LocationSearchDto search, Pageable pageable) {
        Page<Location> page = this.locationRepository.findBySearchCriteria(search, pageable);

        return pageMapper.toPageDto(page, this.locationMapper::locationToLocationDetailDto);
    }

    @Override
    public LocationDetailDto getLocationById(long id) {
        return this.locationMapper.locationToLocationDetailDto(
            this.locationRepository.findById(id).orElseThrow(() -> new NotFoundException("No location with id %s found".formatted(id)))
        );
    }
}
