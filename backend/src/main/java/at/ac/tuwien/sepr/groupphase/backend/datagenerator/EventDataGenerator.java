package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
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
                .title("Guns N' Roses")
                .date(Timestamp.valueOf("2021-06-09 20:00:00"))
                .seatPrice(99.99f)
                .standingPrice(69.99f)
                .eventType(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(0))
                .hall(hallDataGenerator.getTestData().get(0))
                .build(),
            Event.builder()
                .title("AC/DC")
                .date(Timestamp.valueOf("2021-08-10 20:00:00"))
                .seatPrice(79.99f)
                .standingPrice(59.99f)
                .eventType(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(1))
                .build(),
            Event.builder()
                .title("Rammstein")
                .date(Timestamp.valueOf("2021-09-11 20:00:00"))
                .seatPrice(180.00f)
                .standingPrice(150.00f)
                .eventType(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(2))
                .hall(hallDataGenerator.getTestData().get(2))
                .build(),
            Event.builder()
                .title("Lindsey Stirling")
                .date(Timestamp.valueOf("2021-10-12 20:00:00"))
                .seatPrice(200.00f)
                .standingPrice(130.00f)
                .eventType(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(3))
                .hall(hallDataGenerator.getTestData().get(3))
                .build(),
            Event.builder()
                .title("Überdosis G'fühl")
                .date(Timestamp.valueOf("2021-11-13 20:00:00"))
                .seatPrice(30.00f)
                .standingPrice(20.00f)
                .eventType(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(4))
                .hall(hallDataGenerator.getTestData().get(4))
                .build()
        );

        LOGGER.info("generating events");
        return eventRepository.saveAll(events);
    }
}
