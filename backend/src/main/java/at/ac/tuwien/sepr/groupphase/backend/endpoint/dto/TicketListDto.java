package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketListDto {

    private Long id;

    private TicketCategory ticketCategory;

    private Long rowNumber;

    private Long seatNumber;

    private EmbeddedFileDto pdfTicket;

    private UUID uuid;

}
