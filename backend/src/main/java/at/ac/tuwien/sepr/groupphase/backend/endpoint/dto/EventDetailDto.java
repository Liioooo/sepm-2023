package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class EventDetailDto {

    private Long id;

    private String title;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Float seatPrice;

    private Float standingPrice;

    private ArtistDetailDto artist;

    private HallTiersDetailDto hall;

    private EventType type;

    private SeatDto[] occupiedSeats;

    private Integer occupiedStandings;
}
