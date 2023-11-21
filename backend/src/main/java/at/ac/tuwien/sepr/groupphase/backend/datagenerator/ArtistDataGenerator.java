package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepr.groupphase.backend.repository.ArtistRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("generateData")
@Component
public class ArtistDataGenerator extends DataGenerator<Artist> {
    private final ArtistRepository artistRepository;

    public ArtistDataGenerator(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    protected List<Artist> generate() {
        if (artistRepository.count() > 0) {
            LOGGER.info("artist data already generated");
            return null;
        }
        LOGGER.info("generating artists");

        final var artists = List.of(
            Artist.builder()
                .firstname("Taylor")
                .lastname("Swift")
                .build(),
            Artist.builder()
                .firstname("Maren")
                .lastname("Morris")
                .build(),
            Artist.builder()
                .firstname("Thomas")
                .lastname("Rhett")
                .build(),
            Artist.builder()
                .fictionalName("The Weeknd")
                .build(),
            Artist.builder()
                .fictionalName("SEVENTEEN")
                .build()
        );

        return artistRepository.saveAll(artists);
    }
}
