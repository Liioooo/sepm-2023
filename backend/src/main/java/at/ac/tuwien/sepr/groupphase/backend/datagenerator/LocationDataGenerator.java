package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateData")
@Component
public class LocationDataGenerator extends DataGenerator<Location> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LocationRepository locationRepository;

    public LocationDataGenerator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    protected List<Location> generate() {
        if (locationRepository.count() > 0) {
            LOGGER.info("location data already generated");
            return null;
        }
        LOGGER.info("generating locations");

        final var locations = List.of(
            Location.builder()
                .title("Wiener Stadthalle")
                .address("Roland-Rainer-Platz 1")
                .postalCode("1150")
                .city("Wien")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Gasometer")
                .address("Guglgasse 6")
                .postalCode("1110")
                .city("Wien")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Graz")
                .address("Messeplatz 1")
                .postalCode("8010")
                .city("Graz")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Klagenfurt")
                .address("Valentin-Leitgeb-Straße 1")
                .postalCode("9020")
                .city("Klagenfurt")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Linz")
                .address("Willy-Brandt-Platz 1")
                .postalCode("4020")
                .city("Linz")
                .country("Österreich")
                .build()
        );

        return locationRepository.saveAll(locations);
    }
}
