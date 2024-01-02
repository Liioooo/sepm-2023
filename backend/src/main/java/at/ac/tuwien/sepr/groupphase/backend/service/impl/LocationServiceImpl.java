package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
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
    private final HallRepository hallRepository;

    LocationServiceImpl(LocationRepository locationRepository, HallRepository hallRepository) {
        this.locationRepository = locationRepository;
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
    public Hall getHallById(long id) {
        return this.hallRepository.findById(id).orElseThrow(() -> new NotFoundException("No hall with id %s found".formatted(id)));
    }

    @Override
    public Collection<Hall> getAllHalls() {
        return this.hallRepository.findAll();
    }
}
