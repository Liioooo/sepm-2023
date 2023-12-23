package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdateTicketsDto {

    @Valid
    private List<TicketOrderUpdateDto> tickets;

}
