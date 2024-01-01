package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
class EventsEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventDataGenerator eventDataGenerator;

    private final String API_BASE = "/api/v1/events";

    private ApplicationUser user1;

    @BeforeEach()
    void setupData() {
        setupUser2();
    }

    private void setupUser2() {
        user1 = ApplicationUser.builder()
            .id(3L)
            .email("user1@email.com")
            .firstName("User")
            .lastName("user1")
            .role(UserRole.ROLE_USER)
            .location(UserLocation.builder()
                .address("Karlsplatz 13")
                .postalCode("1040")
                .city("Wien")
                .country("Österreich")
                .build())
            .build();
    }

    @Test
    void searchEvents_bySearch_whileLoggedIn_returnsCorrectEvents() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(get(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("user1@email.com").roles("USER"))
                .param("search", "blind")
                .param("page", "0")
                .param("size", "10")
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            PageDto<EventListDto> events = objectMapper.readValue(content, new TypeReference<PageDto<EventListDto>>() {
            });

            assertThat(events.getContent()).containsExactlyInAnyOrder(
                eventMapper.eventToEventListDto(eventDataGenerator.getTestData().get(3))
            );
        });
    }
}
