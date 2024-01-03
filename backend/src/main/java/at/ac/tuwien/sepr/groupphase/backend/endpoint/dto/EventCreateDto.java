package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventCreateDto {

    private String title;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Float seatPrice;

    private Float standingPrice;

    private Long hallId;

    private Long artistId;

    private MultipartFile image;

    private EventType type;
}
