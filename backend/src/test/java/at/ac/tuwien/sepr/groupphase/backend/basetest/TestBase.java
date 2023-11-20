package at.ac.tuwien.sepr.groupphase.backend.basetest;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TestBase {

    @Autowired
    ApplicationUserDataGenerator applicationUserDataGenerator;

    @BeforeEach
    public void setupDb() {
        applicationUserDataGenerator.generateUsers();
    }

    @AfterEach
    public void tearDownDb() {
        applicationUserDataGenerator.clearUsers();
    }

}
