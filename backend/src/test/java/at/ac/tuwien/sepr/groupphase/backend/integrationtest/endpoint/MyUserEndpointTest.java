package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
class MyUserEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    private final String API_BASE = "/api/v1/my-user";
    private final String API_NEWS = "/api/v1/news";

    @Test
    void deleteUser_whileNotLoggedIn_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(delete(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @ParameterizedTest()
    @ValueSource(strings = {"3924gx3z2u43", "-1", "'; DROP TABLE APPLICATION_USER; --"})
    void deleteUser_whileLoggedInAsUnknownUser_isNotFound(String username) {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(delete(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().isNotFound()
            );
        });
    }

    @ParameterizedTest()
    @ValueSource(strings = {"user1@email.com", "user2@email.com", "admin@email.com", "locked@email.com"})
    @DirtiesContext
    void deleteUser_whileLoggedInAsKnownUser_isSuccessful(String username) {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(delete(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().isNoContent()
            );

            assertThat(applicationUserRepository.findUserByEmail(username).isEmpty()).isTrue();
        });
    }

    @ParameterizedTest()
    @ValueSource(strings = {"user1@email.com", "admin@email.com",})
    @DirtiesContext
    void deleteUser_whileLoggedInAsKnownUser_afterReadingNews_isSuccessful(String username) {
        assertDoesNotThrow(() -> {
            // Read Test-News-1 to mark it as read
            this.mockMvc.perform(MockMvcRequestBuilders.get(API_NEWS + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().isOk()
            );

            this.mockMvc.perform(delete(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(username).roles("USER"))
            ).andExpectAll(
                status().isNoContent()
            );

            assertThat(applicationUserRepository.findUserByEmail(username).isEmpty()).isTrue();
        });
    }


}