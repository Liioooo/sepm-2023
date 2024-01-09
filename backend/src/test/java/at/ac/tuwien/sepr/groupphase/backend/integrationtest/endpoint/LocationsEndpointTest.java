package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
class LocationsEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String API_BASE = "/api/v1/locations";

    private LocationCreateDto locationCreateDto;

    @BeforeEach
    void init() {
        initLocationCreateDto();
    }

    void initLocationCreateDto() {
        locationCreateDto = new LocationCreateDto(
            "Test location",
            "Teststraße 12/1",
            "2191",
            "Gaweinstal",
            "Österreich"
        );
    }

    @Test
    void createLocation_whileNotLoggedIn_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationCreateDto))
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void createLocation_whileLoggedInAsNonAdmin_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationCreateDto))
                .with(user("user1@email.com").roles("USER"))
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void createLocation_whileLoggedInAsAdmin_isCreated() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(post(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationCreateDto))
                .with(user("admin@email.com").roles("ADMIN"))
            ).andExpect(
                status().isCreated()
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            LocationDetailDto locationDetailDto = objectMapper.readValue(content, LocationDetailDto.class);

            assertAll(
                () -> assertNotNull(locationDetailDto),
                () -> assertThat(locationDetailDto)
                    .extracting(LocationDetailDto::getTitle, LocationDetailDto::getAddress, LocationDetailDto::getPostalCode, LocationDetailDto::getCity, LocationDetailDto::getCountry)
                    .contains(locationCreateDto.getTitle(), locationCreateDto.getAddress(), locationCreateDto.getPostalCode(), locationCreateDto.getCity(), locationCreateDto.getCountry())
            );
        });
    }
}
