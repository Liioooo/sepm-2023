package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface LocationMapper {

    LocationDetailDto locationToLocationDetailDto(Location location);

    List<LocationDetailDto> locationCollectionToLocationDetailDtoList(Collection<Location> locations);

}
