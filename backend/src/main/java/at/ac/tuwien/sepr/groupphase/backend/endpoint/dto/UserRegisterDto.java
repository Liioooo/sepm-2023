package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserRegisterDto {

    @NotBlank(message = "Email must be set")
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank(message = "First Name must be set")
    @Size(max = 255)
    private String firstName;

    @NotBlank(message = "Last Name must be set")
    @Size(max = 255)
    private String lastName;

    @NotBlank(message = "Password must be set")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm Password must be set")
    @Size(max = 255)
    private String confirmPassword;

    @Valid
    private UserLocationDto location;

    @AssertTrue(message = "Password must match confirm password")
    private boolean getPasswordsMatch() {
        return password.equals(confirmPassword);
    }
}
