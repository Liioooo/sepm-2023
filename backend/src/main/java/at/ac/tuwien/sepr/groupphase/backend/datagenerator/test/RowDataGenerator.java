package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.entity.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

@Profile("generateTestData")
@Component
public class RowDataGenerator extends DataGenerator<Row> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HallDataGenerator hallDataGenerator;

    public RowDataGenerator(HallDataGenerator hallDataGenerator) {
        this.hallDataGenerator = hallDataGenerator;
    }

    @Override
    protected List<Row> generate() {
        LOGGER.info("generating rows");

        Long[][] seatsPerRow = {
            {20L, 20L, 20L, 20L, 20L},
            {20L, 20L, 10L, 10L, 10L},
            {20L, 15L, 11L, 10L},
            {18L, 20L, 20L, 18L, 18L, 18L, 16L, 16L, 12L},
            {19L, 18L, 17L, 15L, 14L},
        };

        final var rows = new LinkedList<Row>();

        for (int hallNumber = 0; hallNumber < seatsPerRow.length; hallNumber++) {
            Long[] row = seatsPerRow[hallNumber];
            for (int rowNumber = 0; rowNumber < row.length; rowNumber++) {
                rows.add(Row.builder()
                    .number((long) rowNumber + 1)
                    .numberOfSeats(row[rowNumber])
                    .hall(hallDataGenerator.getTestData().get(hallNumber))
                    .build()
                );
            }
        }

        return rows;
    }
}
