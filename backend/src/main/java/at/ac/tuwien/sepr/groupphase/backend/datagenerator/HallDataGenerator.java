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
                .name("Halle D")
                .location(locationDataGenerator.getTestData().get(0))
                .standingCount(500L)
                .build(),
            Hall.builder()
                .name("Halle F")
                .location(locationDataGenerator.getTestData().get(0))
                .standingCount(200L)
                .build(),
            Hall.builder()
                .name("Halle Gasometer")
                .location(locationDataGenerator.getTestData().get(1))
                .standingCount(1234L)
                .build(),
            Hall.builder()
                .name("Halle 1")
                .location(locationDataGenerator.getTestData().get(2))
                .standingCount(200L)
                .build(),
            Hall.builder()
                .name("Halle 1")
                .location(locationDataGenerator.getTestData().get(3))
                .standingCount(0L)
                .build(),
            Hall.builder()
                .name("Halle 1")
                .location(locationDataGenerator.getTestData().get(4))
                .standingCount(0L)
                .build(),
            Hall.builder()
                .name("Halle 2")
                .location(locationDataGenerator.getTestData().get(4))
                .standingCount(100L)
                .build(),
            Hall.builder()
                .name("Halle 0")
                .location(locationDataGenerator.getTestData().get(4))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 1")
                .location(locationDataGenerator.getTestData().get(4))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 2")
                .location(locationDataGenerator.getTestData().get(5))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 3")
                .location(locationDataGenerator.getTestData().get(6))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 4")
                .location(locationDataGenerator.getTestData().get(7))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 5")
                .location(locationDataGenerator.getTestData().get(8))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 6")
                .location(locationDataGenerator.getTestData().get(9))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 7")
                .location(locationDataGenerator.getTestData().get(10))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 8")
                .location(locationDataGenerator.getTestData().get(11))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 9")
                .location(locationDataGenerator.getTestData().get(12))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 10")
                .location(locationDataGenerator.getTestData().get(13))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 11")
                .location(locationDataGenerator.getTestData().get(14))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 12")
                .location(locationDataGenerator.getTestData().get(15))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 13")
                .location(locationDataGenerator.getTestData().get(16))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 14")
                .location(locationDataGenerator.getTestData().get(17))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 15")
                .location(locationDataGenerator.getTestData().get(18))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 16")
                .location(locationDataGenerator.getTestData().get(19))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 17")
                .location(locationDataGenerator.getTestData().get(20))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 18")
                .location(locationDataGenerator.getTestData().get(21))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 19")
                .location(locationDataGenerator.getTestData().get(22))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 20")
                .location(locationDataGenerator.getTestData().get(23))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 21")
                .location(locationDataGenerator.getTestData().get(24))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 22")
                .location(locationDataGenerator.getTestData().get(25))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 23")
                .location(locationDataGenerator.getTestData().get(26))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 24")
                .location(locationDataGenerator.getTestData().get(27))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 25")
                .location(locationDataGenerator.getTestData().get(28))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 26")
                .location(locationDataGenerator.getTestData().get(29))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 27")
                .location(locationDataGenerator.getTestData().get(30))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 28")
                .location(locationDataGenerator.getTestData().get(31))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 29")
                .location(locationDataGenerator.getTestData().get(32))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 30")
                .location(locationDataGenerator.getTestData().get(33))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 31")
                .location(locationDataGenerator.getTestData().get(34))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 32")
                .location(locationDataGenerator.getTestData().get(35))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 33")
                .location(locationDataGenerator.getTestData().get(36))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 34")
                .location(locationDataGenerator.getTestData().get(37))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 35")
                .location(locationDataGenerator.getTestData().get(38))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 36")
                .location(locationDataGenerator.getTestData().get(39))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 37")
                .location(locationDataGenerator.getTestData().get(40))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 38")
                .location(locationDataGenerator.getTestData().get(41))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 39")
                .location(locationDataGenerator.getTestData().get(42))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 40")
                .location(locationDataGenerator.getTestData().get(43))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 41")
                .location(locationDataGenerator.getTestData().get(44))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 42")
                .location(locationDataGenerator.getTestData().get(45))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 43")
                .location(locationDataGenerator.getTestData().get(46))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 44")
                .location(locationDataGenerator.getTestData().get(47))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 45")
                .location(locationDataGenerator.getTestData().get(48))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 46")
                .location(locationDataGenerator.getTestData().get(49))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 47")
                .location(locationDataGenerator.getTestData().get(50))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 48")
                .location(locationDataGenerator.getTestData().get(51))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 49")
                .location(locationDataGenerator.getTestData().get(52))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 50")
                .location(locationDataGenerator.getTestData().get(53))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 51")
                .location(locationDataGenerator.getTestData().get(54))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 52")
                .location(locationDataGenerator.getTestData().get(55))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 53")
                .location(locationDataGenerator.getTestData().get(56))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 54")
                .location(locationDataGenerator.getTestData().get(57))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 55")
                .location(locationDataGenerator.getTestData().get(58))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 56")
                .location(locationDataGenerator.getTestData().get(59))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 57")
                .location(locationDataGenerator.getTestData().get(60))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 58")
                .location(locationDataGenerator.getTestData().get(61))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 59")
                .location(locationDataGenerator.getTestData().get(62))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 60")
                .location(locationDataGenerator.getTestData().get(63))
                .standingCount(20L)
                .build(),
            Hall.builder()
                .name("Halle 61")
                .location(locationDataGenerator.getTestData().get(64))
                .standingCount(20L)
                .build()
        );

        return hallRepository.saveAll(halls);
    }
}
