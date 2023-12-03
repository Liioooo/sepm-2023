package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ResetPasswordDto {

    @NotBlank(message = "No token provided")
    private String token;

    @NotBlank(message = "Password must be set")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String newPassword;

}
