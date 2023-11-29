package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class EventCreateDto {

    private String title;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Float seatPrice;

    private Float standingPrice;

    private Integer hallId;

    private Integer artistId;

    private MultipartFile image;

}
