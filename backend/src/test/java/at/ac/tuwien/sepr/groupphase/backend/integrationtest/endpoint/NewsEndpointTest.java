package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
public class NewsEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ApplicationUserRepository userRepository;


    private final String API_BASE = "/api/v1/news";
    private final String API_READ = API_BASE + "/read";
    private final String API_UNREAD = API_BASE + "/unread";

    @Test
    void getSingleNews_whileLoggedInAsKnownUser_showsNewsWithId1() {
        ApplicationUser expectedAuthor = ApplicationUser.builder()
            .email("admin@email.com")
            .firstName("Admin")
            .lastName("Admin")
            .password(passwordEncoder.encode("password"))
            .role(UserRole.ROLE_ADMIN)
            .location(UserLocation.builder()
                .address("Brauhausgasse 1")
                .postalCode("2320")
                .city("Schwechat")
                .country("Österreich")
                .build())
            .build();
    }

    @Test
    void getAllUnReadNews_whileLoggedInAsKnownUser_containsReadNews() {
        String username = "user1@email.com";

        assertDoesNotThrow(() -> {
            var result = this.mockMvc.perform(get(API_UNREAD)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().is(200)
            ).andReturn().getResponse().getContentAsByteArray();

            var newsIterator = objectMapper.readerFor(NewsListDto.class).<NewsListDto>readValues(result);

            List<NewsListDto> news = new ArrayList<>();
            newsIterator.forEachRemaining(news::add);

            assertNotNull(newsIterator);
            assertThat(news).isNotNull();

            assertThat(news)
                .extracting(
                    NewsListDto::getTitle,
                    NewsListDto::getPublishDate,
                    NewsListDto::getOverviewText
                )
                .contains(
                    tuple(
                        "News-Title-1",
                        OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC),
                        "This is an abstract for News-Title-1"
                    ),
                    tuple(
                        "News-Title-2",
                        OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC),
                        "This is an abstract for News-Title-2"
                    ),
                    tuple(
                        "News-Title-3",
                        OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC),
                        "This is an abstract for News-Title-3"
                    )
                );
        });
    }


    @Test
    void getAllReadNews_whileLoggedInAsKnownUser_containsReadNews() {
        String username = "user1@email.com";

        assertDoesNotThrow(() -> {
            // Read Test-News-1 to mark it as read
            this.mockMvc.perform(get(API_BASE + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().is(200)
            );

            // Read Test-News-2 to mark it as read
            this.mockMvc.perform(get(API_BASE + "/2")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().is(200)
            );

            // Get the actual List of read News
            var result = this.mockMvc.perform(get(API_READ)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().is(200)
            ).andReturn().getResponse().getContentAsByteArray();

            var newsIterator = objectMapper.readerFor(NewsListDto.class).<NewsListDto>readValues(result);

            List<NewsListDto> news = new ArrayList<>();
            newsIterator.forEachRemaining(news::add);

            assertNotNull(newsIterator);
            assertThat(news).isNotNull();

            assertThat(news)
                .extracting(
                    NewsListDto::getTitle,
                    NewsListDto::getPublishDate,
                    NewsListDto::getOverviewText
                ).contains(
                    tuple(
                        "News-Title-1",
                        OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC),
                        "This is an abstract for News-Title-1"
                    ),
                    tuple(
                        "News-Title-2",
                        OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC),
                        "This is an abstract for News-Title-2"
                    )
                );
        });
    }
}
