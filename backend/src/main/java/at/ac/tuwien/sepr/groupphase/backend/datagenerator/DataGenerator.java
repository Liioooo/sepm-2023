package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Abstract class for data generators.
 *
 * @param <T> type of entity to generate
 */
public abstract class DataGenerator<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private List<T> testData;
    private boolean isGenerated = false;

    @PostConstruct
    public void generateIfEmpty() {
        if (!isGenerated) {
            testData = generate();
            isGenerated = true;
        }
    }

    protected abstract List<T> generate();

    public List<T> getTestData() {
        if (!isGenerated) {
            testData = generate();
            isGenerated = true;
        }

        return testData;
    }
}
