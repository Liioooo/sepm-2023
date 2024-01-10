package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final HallRepository hallRepository;

    LocationServiceImpl(LocationRepository locationRepository, HallRepository hallRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.hallRepository = hallRepository;
    }

    @Override
    public Page<Location> getLocationsBySearch(LocationSearchDto search, Pageable pageable) {
        return this.locationRepository.findBySearchCriteria(search, pageable);
    }

    @Override
    public Location getLocationById(long id) {
        return this.locationRepository.findById(id).orElseThrow(() -> new NotFoundException("No location with id %s found".formatted(id)));
    }

    @Override
    public Location createLocation(LocationCreateDto locationCreateDto) {
        return this.locationRepository.save(locationMapper.locationCreateDtoToLocation(locationCreateDto));
    }

    @Override
    public Hall getHallById(long id) {
        return this.hallRepository.findById(id).orElseThrow(() -> new NotFoundException("No hall with id %s found".formatted(id)));
    }

    @Override
    public Collection<Hall> getAllHalls() {
        return this.hallRepository.findAll();
    }
}
