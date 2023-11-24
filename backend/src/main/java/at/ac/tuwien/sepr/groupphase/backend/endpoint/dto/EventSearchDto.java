package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

    @Size(max = 255)
    String location;

    LocalDateTime timeStart;

    LocalDateTime timeEnd;

    @PositiveOrZero
    Float priceMax;
}
