package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class EventListDto {

    private Long id;

    private String title;

    private Timestamp date;

    private ArtistDetailDto artist;

    private HallDetailDto hall;

}
