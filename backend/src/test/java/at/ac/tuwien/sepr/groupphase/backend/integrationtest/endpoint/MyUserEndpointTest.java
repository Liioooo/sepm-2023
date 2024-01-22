package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ApplicationUserMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Autowired
    ApplicationUserDataGenerator userDataGenerator;

    @Autowired
    ApplicationUserMapper applicationUserMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String API_BASE = "/api/v1/my-user";
    private final String API_NEWS = "/api/v1/news";

    private ApplicationUser user1;
    private ApplicationUser admin1;

    @BeforeEach()
    void setupData() {
        setupUsers();
    }

    void setupUsers() {
        admin1 = userDataGenerator.getTestData().get(0);
        user1 = userDataGenerator.getTestData().get(4);
    }

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

    @Test
    void getUserDetails_whileLoggedInAsUser_returnsUserDetails() {
        assertDoesNotThrow(() -> {
            byte[] returned = this.mockMvc.perform(MockMvcRequestBuilders.get(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user1.getUsername()).roles("USER"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            UserDetailDto userDetailDto = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(returned).next();

            assertThat(userDetailDto).isEqualTo(applicationUserMapper.applicationUserToUserDetailDto(user1));
        });
    }

    @Test
    @DirtiesContext
    void updateUserDetails_whileLoggedInAsUser_successFullyUpdatesUserDetails() {
        UserUpdateDetailDto toUpdate = UserUpdateDetailDto.builder()
            .email("user1_updated@email.com")
            .firstName("user1_firstName_updated")
            .lastName("user1_lastName_updated")
            .password("ChangedPassword123!")
            .confirmPassword("ChangedPassword123!")
            .build();

        assertDoesNotThrow(() -> {
            byte[] body = this.mockMvc.perform(MockMvcRequestBuilders.put(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(user1.getUsername()).roles("USER"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();


            // Get respository
            ApplicationUser savedUser = applicationUserRepository.findById(user1.getId())
                .orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(user1.getId())));

            // Get returned user-content
            UserDetailDto returnedUser = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).next();

            assertAll(
                // verify repository information
                () -> assertThat(savedUser)
                    .extracting(
                        ApplicationUser::getFirstName,
                        ApplicationUser::getLastName,
                        ApplicationUser::getEmail,
                        ApplicationUser::getRole,
                        ApplicationUser::getFailedAuths,
                        ApplicationUser::isLocked
                    ).containsExactly(
                        toUpdate.getFirstName(),
                        toUpdate.getLastName(),
                        toUpdate.getEmail(),
                        user1.getRole(),
                        user1.getFailedAuths(),
                        user1.isLocked()
                    ),

                // Verify repository password
                () -> assertTrue(passwordEncoder.matches(toUpdate.getPassword(), savedUser.getPassword())),

                // verify returned information
                () -> assertThat(returnedUser)
                    .extracting(
                        UserDetailDto::getFirstName,
                        UserDetailDto::getLastName,
                        UserDetailDto::getEmail,
                        UserDetailDto::getRole
                    ).containsExactly(
                        toUpdate.getFirstName(),
                        toUpdate.getLastName(),
                        toUpdate.getEmail(),
                        user1.getRole()
                    )
            );
        });
    }
}