package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RowListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class HallEndpointTest {
    private final String API_BASE = "/api/v1/halls";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private HallCreateDto hallCreateDto;
    @Autowired
    private LocationRepository locationRepository;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public LocationRepository locationRepository() {
            LocationRepository locationRepository = Mockito.mock(LocationRepository.class);
            Mockito.when(locationRepository.findById(1L)).thenReturn(Optional.of(new Location(1L, "Testlocation", "Teststreet", "1012", "Wien", "Österreich", List.of())));
            return locationRepository;
        }
    }

    @BeforeEach
    void init() {
        initHallCreateDto();
    }

    void initHallCreateDto() {
        hallCreateDto = new HallCreateDto(
            "Test hall",
            20L,
            1L,
            new RowListDto[] {}
        );
    }

    @Test
    void createHall_whileNotLoggedIn_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hallCreateDto))
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void createHall_whileLoggedInAsNonAdmin_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hallCreateDto))
                .with(user("user1@email.com").roles("USER"))
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void createHall_whileLoggedInAsAdmin_isCreated() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hallCreateDto))
                .with(user("admin@email.com").roles("ADMIN"))
            ).andExpect(
                status().isCreated()
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            HallDetailDto hallDetailDto = objectMapper.readValue(content, HallDetailDto.class);

            assertAll(
                () -> assertNotNull(hallDetailDto),
                () -> assertThat(hallDetailDto)
                    .extracting(HallDetailDto::getName, HallDetailDto::getStandingCount, HallDetailDto::getLocation)
                    .contains(hallCreateDto.getName(), hallCreateDto.getStandingCount(), new LocationDetailDto(1L, "Testlocation", "Teststreet", "1012", "Wien", "Österreich"))
            );
        });
    }
}
