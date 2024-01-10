package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import org.mapstruct.Mapper;

@Mapper
public interface HallMapper {
    Hall hallCreateDtoToHall(HallCreateDto hallCreateDto);

    HallDetailDto hallToHallDetailDto(Hall hall);
}
