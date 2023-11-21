package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UpdateUserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ApplicationUserMapper;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/my-user")
public class MyUserEndpoint {

    private final UserService userService;

    private final ApplicationUserMapper applicationUserMapper;

    public MyUserEndpoint(UserService userService, ApplicationUserMapper applicationUserMapper) {
        this.userService = userService;
        this.applicationUserMapper = applicationUserMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping()
    @Operation(summary = "Get details for the currently logged in user")
    public UserDetailDto getUserDetails() {
        return applicationUserMapper.applicationUserToUserDetailDto(
            userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in"))
        );
    }

    @Secured("ROLE_USER")
    @PutMapping()
    @Operation(summary = "Update details for the currently logged in user")
    public UserDetailDto updateUserDetails(@Valid @RequestBody UpdateUserDetailDto updateUserDetailDto) {
        return applicationUserMapper.applicationUserToUserDetailDto(userService.updateAuthenticatedUser(updateUserDetailDto));
    }
}
