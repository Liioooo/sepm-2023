package at.ac.tuwien.sepr.groupphase.backend.entity;

import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private OffsetDateTime startDate;

    @Column(nullable = false)
    private OffsetDateTime endDate;

    @Column(nullable = false, precision = 2)
    private Float seatPrice;

    @Column(nullable = false, precision = 2)
    private Float standingPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hall hall;

}
