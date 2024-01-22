package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UserSearchDto {
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;
}
