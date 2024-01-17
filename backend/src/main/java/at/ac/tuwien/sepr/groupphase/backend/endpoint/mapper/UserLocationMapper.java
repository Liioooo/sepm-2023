package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLocationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import org.mapstruct.Mapper;

@Mapper
public interface UserLocationMapper {

    UserLocation userLocationDtoToUserLocation(UserLocationDto userLocationDto);

    UserLocationDto userLocationToUserLocationDto(UserLocation userLocationDto);
}
