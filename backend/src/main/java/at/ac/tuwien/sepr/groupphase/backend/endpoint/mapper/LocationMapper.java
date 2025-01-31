package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationHallsDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface LocationMapper {

    LocationDetailDto locationToLocationDetailDto(Location location);

    List<LocationDetailDto> locationCollectionToLocationDetailDtoList(Collection<Location> locations);

    Location locationCreateDtoToLocation(LocationCreateDto locationCreateDto);

    LocationHallsDto locationToLocationHallsDto(Location location);

    List<HallDetailDto> hallCollectionToHallDetailDtoList(Collection<Hall> halls);

}
