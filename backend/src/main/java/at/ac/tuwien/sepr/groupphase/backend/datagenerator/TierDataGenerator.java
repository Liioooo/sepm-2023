package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tier;
import at.ac.tuwien.sepr.groupphase.backend.repository.TierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Profile("generateData")
@Component
public class TierDataGenerator extends DataGenerator<Tier> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TierRepository tierRepository;

    private final HallDataGenerator hallDataGenerator;

    public TierDataGenerator(TierRepository tierRepository, HallDataGenerator hallDataGenerator) {
        this.tierRepository = tierRepository;
        this.hallDataGenerator = hallDataGenerator;
    }

    @Override
    protected List<Tier> generate() {
        if (tierRepository.count() > 0) {
            LOGGER.info("tier data already generated");
            return null;
        }
        LOGGER.info("generating tiers");

        Long[][] seatsPerTier = {
            {20L, 20L, 20L, 20L, 20L},
            {20L, 20L, 10L, 10L, 10L},
            {20L, 15L, 11L, 10L},
            {18L, 20L, 20L, 18L, 18L, 18L, 16L, 16L, 12L},
            {19L, 18L, 17L, 15L, 14L},
        };

        final var tiers = new LinkedList<Tier>();

        for (int hallNumber = 0; hallNumber < seatsPerTier.length; hallNumber++) {
            Long[] tier = seatsPerTier[hallNumber];
            for (int rowNumber = 0; rowNumber < tier.length; rowNumber++) {
                tiers.add(Tier.builder()
                    .number((long) rowNumber + 1)
                    .numberOfSeats(tier[rowNumber])
                    .hall(hallDataGenerator.getTestData().get(hallNumber))
                    .build()
                );
            }
        }

        return tierRepository.saveAll(tiers);
    }
}
