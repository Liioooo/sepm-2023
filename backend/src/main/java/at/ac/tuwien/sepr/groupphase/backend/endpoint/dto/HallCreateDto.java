package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HallCreateDto {
    @NotNull
    @Length(min = 1)
    private String name;

    @NotNull
    @Min(0)
    private Long standingCount;

    @NotNull
    @Min(0)
    private Long locationId;

    private RowListDto[] rows;
}
