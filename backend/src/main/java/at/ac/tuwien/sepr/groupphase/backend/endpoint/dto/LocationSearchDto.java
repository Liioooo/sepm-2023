package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

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
public class LocationSearchDto {

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String postalCode;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String country;

}
