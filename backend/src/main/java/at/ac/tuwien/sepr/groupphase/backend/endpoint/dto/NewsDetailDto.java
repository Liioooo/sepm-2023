package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
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
public class NewsDetailDto {
    private Long id;
    private String title;
    private String text;
    private String overviewText;
    private String authorName;
    private OffsetDateTime publishDate;
    private PublicFile image;
}
