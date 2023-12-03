package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventSearchDto {
    @Size(max = 255)
    String search;

    @Size(max = 255)
    String artist;

    @Size(max = 255)
    String title;

    Long locationId;

    OffsetDateTime timeStart;

    OffsetDateTime timeEnd;

    @PositiveOrZero
    Float priceMax;

    EventType type;

    @PositiveOrZero
    Float duration;

    public Duration getDurationAsDuration() {
        if (duration == null) {
            return null;
        }

        return Duration.ofHours(duration.intValue()).plusMinutes((long) ((duration % 1) * 60));
    }
}
