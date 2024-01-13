package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("generateTestData")
@Component
public class ArtistDataGenerator extends DataGenerator<Artist> {
    @Override
    protected List<Artist> generate() {
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
                .fictionalName("Rammstein")
                .build(),
            Artist.builder()
                .fictionalName("The Weeknd")
                .build(),
            Artist.builder()
                .fictionalName("SEVENTEEN")
                .build(),
            Artist.builder()
                .fictionalName("reintermediate rich applications")
                .build(),
            Artist.builder()
                .fictionalName("disintermediate holistic niches")
                .build(),
            Artist.builder()
                .fictionalName("grow rich infrastructures")
                .build(),
            Artist.builder()
                .fictionalName("revolutionize dot-com applications")
                .build(),
            Artist.builder()
                .fictionalName("optimize vertical partnerships")
                .build(),
            Artist.builder()
                .fictionalName("engage compelling action-items")
                .build(),
            Artist.builder()
                .fictionalName("transform robust paradigms")
                .build(),
            Artist.builder()
                .fictionalName("optimize scalable infrastructures")
                .build(),
            Artist.builder()
                .fictionalName("exploit virtual communities")
                .build(),
            Artist.builder()
                .fictionalName("generate global deliverables")
                .build(),
            Artist.builder()
                .fictionalName("disintermediate world-class convergence")
                .build(),
            Artist.builder()
                .fictionalName("cultivate vertical channels")
                .build(),
            Artist.builder()
                .fictionalName("cultivate next-generation methodologies")
                .build(),
            Artist.builder()
                .fictionalName("grow sticky supply-chains")
                .build(),
            Artist.builder()
                .fictionalName("leverage dot-com initiatives")
                .build(),
            Artist.builder()
                .fictionalName("productize out-of-the-box action-items")
                .build(),
            Artist.builder()
                .fictionalName("disintermediate holistic e-business")
                .build(),
            Artist.builder()
                .fictionalName("cultivate mission-critical methodologies")
                .build(),
            Artist.builder()
                .fictionalName("optimize virtual networks")
                .build(),
            Artist.builder()
                .fictionalName("integrate best-of-breed users")
                .build(),
            Artist.builder()
                .fictionalName("synthesize real-time e-services")
                .build(),
            Artist.builder()
                .fictionalName("empower cross-media markets")
                .build(),
            Artist.builder()
                .fictionalName("embrace 24/7 e-markets")
                .build(),
            Artist.builder()
                .fictionalName("streamline transparent e-business")
                .build(),
            Artist.builder()
                .fictionalName("generate vertical schemas")
                .build(),
            Artist.builder()
                .fictionalName("syndicate next-generation models")
                .build(),
            Artist.builder()
                .fictionalName("benchmark next-generation deliverables")
                .build(),
            Artist.builder()
                .fictionalName("revolutionize cross-platform action-items")
                .build(),
            Artist.builder()
                .fictionalName("maximize cutting-edge interfaces")
                .build(),
            Artist.builder()
                .fictionalName("synthesize cross-platform web services")
                .build(),
            Artist.builder()
                .fictionalName("orchestrate intuitive portals")
                .build(),
            Artist.builder()
                .fictionalName("utilize value-added architectures")
                .build(),
            Artist.builder()
                .fictionalName("strategize impactful content")
                .build(),
            Artist.builder()
                .fictionalName("seize interactive technologies")
                .build(),
            Artist.builder()
                .fictionalName("empower front-end action-items")
                .build(),
            Artist.builder()
                .fictionalName("engineer holistic ROI")
                .build(),
            Artist.builder()
                .fictionalName("incentivize wireless paradigms")
                .build(),
            Artist.builder()
                .fictionalName("repurpose mission-critical interfaces")
                .build(),
            Artist.builder()
                .fictionalName("generate best-of-breed partnerships")
                .build(),
            Artist.builder()
                .fictionalName("orchestrate intuitive bandwidth")
                .build(),
            Artist.builder()
                .fictionalName("implement cutting-edge deliverables")
                .build(),
            Artist.builder()
                .fictionalName("enhance enterprise niches")
                .build(),
            Artist.builder()
                .fictionalName("productize holistic e-commerce")
                .build(),
            Artist.builder()
                .fictionalName("scale plug-and-play systems")
                .build(),
            Artist.builder()
                .fictionalName("revolutionize revolutionary deliverables")
                .build(),
            Artist.builder()
                .fictionalName("synergize leading-edge infrastructures")
                .build(),
            Artist.builder()
                .fictionalName("integrate world-class networks")
                .build(),
            Artist.builder()
                .fictionalName("orchestrate leading-edge eyeballs")
                .build(),
            Artist.builder()
                .fictionalName("morph efficient e-business")
                .build(),
            Artist.builder()
                .fictionalName("evolve open-source functionalities")
                .build(),
            Artist.builder()
                .fictionalName("engage transparent deliverables")
                .build(),
            Artist.builder()
                .fictionalName("enable proactive niches")
                .build(),
            Artist.builder()
                .fictionalName("enhance B2B metrics")
                .build(),
            Artist.builder()
                .fictionalName("integrate strategic networks")
                .build(),
            Artist.builder()
                .fictionalName("reinvent proactive systems")
                .build(),
            Artist.builder()
                .fictionalName("synergize vertical infomediaries")
                .build(),
            Artist.builder()
                .fictionalName("morph open-source partnerships")
                .build(),
            Artist.builder()
                .fictionalName("maximize vertical infrastructures")
                .build(),
            Artist.builder()
                .fictionalName("architect web-enabled markets")
                .build(),
            Artist.builder()
                .fictionalName("utilize transparent schemas")
                .build(),
            Artist.builder()
                .fictionalName("Felix Lobrecht") //66?
                .build(),
            Artist.builder()
                .fictionalName("Felix Lobrecht und Tommy Schmitt")
                .build(),
            Artist.builder()
                .fictionalName("Dagi Bee")
                .build(),
            Artist.builder()
                .fictionalName("Luca und Sandra")
                .build(),
            Artist.builder()
                .fictionalName("Rezo und Ju") // 70
                .build(),
            Artist.builder()
                .fictionalName("Alex Peter")
                .build(),
            Artist.builder()
                .fictionalName("Joe Rogan")
                .build(),
            Artist.builder()
                .fictionalName("Matt and Abby")
                .build(),
            Artist.builder()
                .fictionalName("Ana und Tim")
                .build(),
            Artist.builder()
                .fictionalName("Jess and Gabriel")
                .build(), // 74
            Artist.builder()
                .fictionalName("Julia und Joey")
                .build(), // 75
            Artist.builder()
                .fictionalName("Wiener Musical Gruppe B")
                .build(),
            Artist.builder()
                .fictionalName("UNKNOWN")
                .build(),
            Artist.builder()
                .fictionalName("STS")
                .build()
        );

        return artists;
    }
}
