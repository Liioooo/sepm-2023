package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.HallService;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {
    private final HallMapper hallMapper;
    private final HallRepository hallRepository;
    private final LocationRepository locationRepository;

    public HallServiceImpl(HallMapper hallMapper, HallRepository hallRepository, LocationRepository locationRepository) {
        this.hallMapper = hallMapper;
        this.hallRepository = hallRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Hall createHall(HallCreateDto hallCreateDto) {
        Hall hall = hallMapper.hallCreateDtoToHall(hallCreateDto);
        hall.getRows().forEach(r -> r.setHall(hall));
        hall.setLocation(locationRepository.findById(hallCreateDto.getLocationId()).orElseThrow(() -> new ConflictException("Invalid location.")));
        return hallRepository.save(hall);
    }
}
