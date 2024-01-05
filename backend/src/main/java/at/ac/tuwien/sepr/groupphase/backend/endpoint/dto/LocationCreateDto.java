package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationCreateDto {
    @NotNull
    private String title;

    @NotNull
    private String address;

    @NotNull
    private String postalCode;

    @NotNull
    private String city;

    @NotNull
    private String country;
}
