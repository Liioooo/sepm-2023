package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventListDto {

    private Long id;

    private String title;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Float seatPrice;

    private Float standingPrice;

    private ArtistDetailDto artist;

    private HallDetailDto hall;

    private EventType type;

}
