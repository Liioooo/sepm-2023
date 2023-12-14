package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Validated
public class UserLocationDto {

    @NotBlank(message = "Address must be set")
    @Size(max = 255)
    private String address;

    @NotBlank(message = "Postal code must be set")
    @Size(max = 255)
    private String postalCode;

    @NotBlank(message = "City must be set")
    @Size(max = 255)
    private String city;

    @NotBlank(message = "Country must be set")
    @Size(max = 255)
    private String country;
}
