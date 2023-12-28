package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewsCreateDto {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not be longer than 255 characters")
    private String title;

    @NotBlank(message = "Overview text is required")
    @Size(max = 1024, message = "Overview text not be longer than 1024 characters")
    private String overviewText;

    @NotBlank(message = "Text is required")
    private String text;

    private MultipartFile image;
}
