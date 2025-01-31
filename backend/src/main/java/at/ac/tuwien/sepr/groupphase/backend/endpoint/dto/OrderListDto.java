package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderListDto {

    private Long id;

    private OrderType orderType;

    private EventListDto event;

    private OffsetDateTime orderDate;

    private EmbeddedFileDto pdfTickets;

    private EmbeddedFileDto receipt;

    private List<EmbeddedFileDto> cancellationReceipts;

}
