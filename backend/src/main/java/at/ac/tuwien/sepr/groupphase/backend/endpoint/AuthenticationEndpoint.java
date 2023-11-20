package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthenticationEndpoint {

    private final UserService userService;

    public AuthenticationEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping(path = "/login")
    @Operation(summary = "Login as a User")
    public String login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PermitAll
    @PostMapping(path = "/register")
    @Operation(summary = "Register as a new User")
    public String register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }
}
