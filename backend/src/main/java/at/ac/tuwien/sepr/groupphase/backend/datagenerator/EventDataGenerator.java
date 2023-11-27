package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Profile("generateData")
@Component
public class EventDataGenerator extends DataGenerator<Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;
    private final ArtistDataGenerator artistDataGenerator;
    private final HallDataGenerator hallDataGenerator;

    public EventDataGenerator(
        EventRepository eventRepository,
        ArtistDataGenerator artistDataGenerator,
        HallDataGenerator hallDataGenerator
    ) {
        this.eventRepository = eventRepository;
        this.artistDataGenerator = artistDataGenerator;
        this.hallDataGenerator = hallDataGenerator;
    }

    @Override
    protected List<Event> generate() {
        if (eventRepository.count() > 0) {
            LOGGER.info("event data already generated");
            return null;
        }

        final var events = List.of(
            Event.builder()
                .title("The Eras Tour")
                .startDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2023, 12, 9, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(99.99f)
                .standingPrice(69.99f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(0))
                .hall(hallDataGenerator.getTestData().get(0))
                .build(),
            Event.builder()
                .title("Troubadour")
                .startDate(OffsetDateTime.of(2023, 12, 10, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2023, 12, 10, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(79.99f)
                .standingPrice(59.99f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(1))
                .build(),
            Event.builder()
                .title("Deutschland")
                .startDate(OffsetDateTime.of(2024, 2, 11, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 2, 11, 22, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(180.00f)
                .standingPrice(150.00f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(2))
                .hall(hallDataGenerator.getTestData().get(2))
                .build(),
            Event.builder()
                .title("Blinding lights")
                .startDate(OffsetDateTime.of(2024, 10, 12, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 12, 23, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(200.00f)
                .standingPrice(130.00f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(3))
                .hall(hallDataGenerator.getTestData().get(3))
                .build(),
            Event.builder()
                .title("파이팅!!!!!!")
                .startDate(OffsetDateTime.of(2024, 11, 13, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 13, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(30.00f)
                .standingPrice(20.00f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(4))
                .hall(hallDataGenerator.getTestData().get(4))
                .build()
        );

        LOGGER.info("generating events");
        return eventRepository.saveAll(events);
    }
}
