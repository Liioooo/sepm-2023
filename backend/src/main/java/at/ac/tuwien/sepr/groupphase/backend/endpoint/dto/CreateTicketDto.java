package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import jakarta.validation.constraints.Min;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateTicketDto {
    @NotNull(message = "The ticket must have a category")
    private TicketCategory category;

    @NotNull(message = "The ticket must be associated with an event")
    private Long eventId;

    @Min(0)
    private Long seatNumber;

    @Min(0)
    private Long tierNumber;
}
