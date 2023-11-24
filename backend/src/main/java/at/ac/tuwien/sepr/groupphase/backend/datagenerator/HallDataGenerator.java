package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.repository.HallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateData")
@Component
public class HallDataGenerator extends DataGenerator<Hall> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HallRepository hallRepository;
    private final LocationDataGenerator locationDataGenerator;

    public HallDataGenerator(HallRepository hallRepository, LocationDataGenerator locationDataGenerator) {
        this.hallRepository = hallRepository;
        this.locationDataGenerator = locationDataGenerator;
    }

    @Override
    protected List<Hall> generate() {
        if (hallRepository.count() > 0) {
            LOGGER.info("hall data already generated");
            return null;
        }
        LOGGER.info("generating halls");

        final var halls = List.of(
            Hall.builder()
                .name("Wiener Stadthalle")
                .location(locationDataGenerator.getTestData().get(0))
                .standingCount(500L)
                .build(),
            Hall.builder()
                .name("Gasometer")
                .location(locationDataGenerator.getTestData().get(1))
                .standingCount(1234L)
                .build(),
            Hall.builder()
                .name("Stadthalle Graz")
                .location(locationDataGenerator.getTestData().get(2))
                .standingCount(200L)
                .build(),
            Hall.builder()
                .name("Stadthalle Klagenfurt")
                .location(locationDataGenerator.getTestData().get(3))
                .standingCount(0L)
                .build(),
            Hall.builder()
                .name("Stadthalle Linz")
                .location(locationDataGenerator.getTestData().get(4))
                .standingCount(0L)
                .build()
        );

        return hallRepository.saveAll(halls);
    }
}
