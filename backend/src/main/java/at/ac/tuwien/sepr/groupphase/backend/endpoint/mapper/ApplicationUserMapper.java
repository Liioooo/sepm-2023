package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationUserMapper {

    UserDetailDto applicationUserToUserDetailDto(ApplicationUser applicationUser);

    UserListDto toUserListDto(ApplicationUser user);

}
