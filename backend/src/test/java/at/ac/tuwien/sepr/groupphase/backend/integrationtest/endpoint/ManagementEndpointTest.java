package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateManagementDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
class ManagementEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventDataGenerator eventDataGenerator;

    @Autowired
    private ApplicationUserRepository userRepository;
    private final String API_BASE = "/api/v1/management";

    private final String API_USERS = API_BASE + "/users";

    private final Long USER1_ID = 3L; // user1@email.com, Not Locked
    private final Long USER2_ID = 3L; // user2@email.com, Not Locked

    private final Long LOCKED_USER_ID = 5L; // locked@email.com,  Locked

    private final Long ADMIN_ID = 1L;

    private final String ADMIN_EMAIL = "admin@email.com";


    @BeforeEach()
    void setupData() {
    }

    @Test
    @DirtiesContext
    void lockUser_loggedInAsAdmin_successfullyLocksUser() {
        Long toLock = USER1_ID;
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(true).build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS + "/" + toLock)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(ADMIN_EMAIL).roles("ADMIN"))
            ).andExpectAll(
                status().isOk()
            );

            ApplicationUser savedUser = userRepository.findById(toLock).orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(toLock)));
            assertTrue(savedUser.isLocked());
        });
    }

    @Test
    void lockOwnAccount_loggedInAsAdmin_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(true).build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS + "/" + ADMIN_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(ADMIN_EMAIL).roles("ADMIN"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void lockUser_notLoggedIn_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(true).build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS + "/" + USER2_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void lockUser_LoggedInAsNonAdmin_returnsUnauthorized() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(false).build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS + "/" + USER1_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    @DirtiesContext
    void unlockUser_loggedInAsAdmin_successfullyUnlocksUser() {
        Long toLock = LOCKED_USER_ID;
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(false).build();

        assertDoesNotThrow(() -> {
            byte[] body = this.mockMvc.perform(patch(API_USERS + "/" + toLock)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(ADMIN_EMAIL).roles("ADMIN"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();


            // Verify respository
            ApplicationUser savedUser = userRepository.findById(toLock).orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(toLock)));
            assertFalse(savedUser.isLocked());

            // Verify returned user-content
        });
    }


    @Test
    void unlockUser_loggedInAsNonAdmin_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto.builder().isLocked(false).build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS + "/" + LOCKED_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

}
