package at.ac.tuwien.sepr.groupphase.backend.unittest.repository;

import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateTestData"})
@DirtiesContext(classMode = AFTER_CLASS)
public class NewsRespositoryTest {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Test
    void findAllByReadByNotContains_loggedIn_showsNews() {

    }
}
