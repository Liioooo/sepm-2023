package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class NewsCreateDto {
    private String title;
    private String overviewText;
    private String text;
    private MultipartFile image;
}
