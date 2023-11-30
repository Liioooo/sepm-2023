package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public List<LocationDetailDto> getLocationsBySearch(LocationSearchDto search) {
        return locationMapper.locationCollectionToLocationDetailDtoList(
            this.locationRepository.findBySearchCriteria(search)
        );
    }

    @Override
    public LocationDetailDto getLocationById(long id) {
        return this.locationMapper.locationToLocationDetailDto(
            this.locationRepository.findById(id).orElseThrow(() -> new NotFoundException("No location with id %s found".formatted(id)))
        );
    }
}
