package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.Valid;
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
public class RedeemReservationDto {

    @NotNull(message = "The order must contain tickets")
    @Size(min = 1, message = "The order must contain at least one ticket")
    @Valid
    private TicketCreateDto[] tickets;

}
