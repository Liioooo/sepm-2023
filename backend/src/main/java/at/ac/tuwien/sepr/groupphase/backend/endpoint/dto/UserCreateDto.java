package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateDto {

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
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm Password must be set")
    @Size(min = 8, max = 255)
    private String confirmPassword;

    @Valid
    private UserLocationDto location;

    @NotNull(message = "Select if locked or unlocked")
    private Boolean isLocked;

    @NotNull(message = "Select a role")
    private UserRole role;

    @AssertTrue(message = "Password must match confirm password")
    private boolean getPasswordsMatch() {
        return password.equals(confirmPassword);
    }
}
