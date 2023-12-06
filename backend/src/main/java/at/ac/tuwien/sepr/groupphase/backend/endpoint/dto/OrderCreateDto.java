package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class OrderCreateDto {
    @NotNull(message = "The order must contain tickets")
    @Size(min = 1, message = "The order must contain at least one ticket")
    private List<CreateTicketDto> tickets;

    @NotNull(message = "The order type must be specified")
    private OrderType orderType;
}
