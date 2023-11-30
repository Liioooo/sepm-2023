package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;
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
    private String text;
    private String overviewText;
    @NotNull
    private MultipartFile image;
}
