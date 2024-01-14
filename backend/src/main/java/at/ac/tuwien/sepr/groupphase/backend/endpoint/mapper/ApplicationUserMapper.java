package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationUserMapper {

    @Mapping(source = "locked", target = "isLocked")
    UserDetailDto applicationUserToUserDetailDto(ApplicationUser applicationUser);

    @Mapping(source = "locked", target = "isLocked")
    UserListDto toUserListDto(ApplicationUser user);

}
