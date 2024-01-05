package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.HallService;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {
    private final HallMapper hallMapper;
    private final HallRepository hallRepository;

    public HallServiceImpl(HallMapper hallMapper, HallRepository hallRepository) {
        this.hallMapper = hallMapper;
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall createHall(HallCreateDto hallCreateDto) {
        Hall hall = hallMapper.hallCreateDtoToHall(hallCreateDto);
        hall.getRows().forEach(r -> r.setHall(hall));
        return hallRepository.save(hall);
    }
}
