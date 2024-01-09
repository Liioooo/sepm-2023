package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmailResetDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ResetPasswordDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PermitAll
    @PostMapping(path = "/send-password-reset-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sends Password Reset Email")
    public void sendPasswordResetEmail(@Valid @RequestBody EmailResetDto emailResetDto) {
        userService.sendPasswordResetEmail(emailResetDto);
    }

    @PermitAll
    @PostMapping(path = "/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Resets Password with provided token")
    public void resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            userService.resetPassword(resetPasswordDto);
        } catch (JwtException e) {
            throw new UnauthorizedException("Invalid Token", e);
        }
    }
}
