package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Start Date is required")
    private OffsetDateTime startDate;

    @NotNull(message = "End Date is required")
    private OffsetDateTime endDate;

    @NotNull(message = "Seat Price is required")
    @PositiveOrZero(message = "Seat Price must be greater or equal to 0")
    private Float seatPrice;

    @NotNull(message = "Standing Price is required")
    @PositiveOrZero(message = "Standing Price must be greater or equal to 0")
    private Float standingPrice;

    @NotNull(message = "Hall ID is required")
    private Long hallId;

    @NotNull(message = "Artist ID is required")
    private Long artistId;

    private MultipartFile image;

    @NotNull(message = "Event Type is required")
    private EventType type;

    @AssertTrue(message = "Start time must be before end time")
    private boolean getStartAfterEnd() {
        return startDate.isBefore(endDate);
    }


}
