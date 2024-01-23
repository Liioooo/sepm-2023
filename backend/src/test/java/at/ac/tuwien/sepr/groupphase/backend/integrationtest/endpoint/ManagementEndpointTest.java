package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ArtistDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.HallDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ArtistDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLocationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateManagementDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class ManagementEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private ApplicationUserDataGenerator userDataGenerator;

    @Autowired
    private EventDataGenerator eventDataGenerator;

    @Autowired
    private ArtistDataGenerator artistDataGenerator;

    @Autowired
    private HallDataGenerator hallDataGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String API_BASE = "/api/v1/management";

    private final String API_USERS = API_BASE + "/users";
    private final String API_EVENTS = API_BASE + "/events";
    private final String API_NEWS = API_BASE + "/news";
    private final String API_ARTISTS = API_BASE + "/artists";
    private final String API_HALLS = API_BASE + "/halls";


    private ApplicationUser user1_info;
    private ApplicationUser user2_info;
    private ApplicationUser lockedUser_info;
    private ApplicationUser admin1_info;
    private UserCreateDto userCreateDto_genericUser;
    private UserCreateDto userCreateDto_adminUser;


    @BeforeEach()
    void setupData() {
        initUser();
        initAdminUser();
        initCreateDto();
    }

    void initCreateDto() {
        userCreateDto_genericUser = new UserCreateDto(
            "created_user1@email.com",
            "created_firstname",
            "created_lastname",
            "password",
            "password",
            new UserLocationDto("Testgasse 1", "1110", "Vienna", "Austria"),
            false,
            UserRole.ROLE_USER
        );

        userCreateDto_adminUser = new UserCreateDto(
            "created_admin1@email.com",
            "created_firstname",
            "created_lastname",
            "admin_password",
            "admin_password",
            new UserLocationDto("Admingasse 12/29", "1010", "Berlin", "Germany"),
            false,
            UserRole.ROLE_ADMIN
        );
    }

    void initUser() {
        user1_info = userDataGenerator.getTestData().get(2);

        user2_info = userDataGenerator.getTestData().get(3);

        lockedUser_info = userDataGenerator.getTestData().get(8);
    }

    void initAdminUser() {
        admin1_info = userDataGenerator.getTestData().get(0);
    }

    @Test
    void getAllEvents_loggedInAsAdmin_successfullyGetsAllEvents() {
        assertDoesNotThrow(() -> {
            // Get the actual List of read News
            int page = 0; // Page number
            int size = 10; // Page size

            byte[] result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_EVENTS)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(admin1_info.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            PageDto<EventListDto> pageDto = objectMapper.readValue(result, new TypeReference<>() {
            });

            List<EventListDto> returnedEvents = pageDto.getContent();

            assertAll(
                () -> assertThat(returnedEvents).isNotNull(),
                () -> assertThat(returnedEvents.size()).isEqualTo(size),
                () -> assertThat(returnedEvents)
                    .extracting(
                        EventListDto::getId,
                        EventListDto::getTitle,
                        EventListDto::getStartDate,
                        EventListDto::getEndDate,
                        EventListDto::getSeatPrice,
                        EventListDto::getStandingPrice,
                        EventListDto::getType
                    ).contains(
                        tuple(
                            eventDataGenerator.getTestData().get(2).getId(),
                            eventDataGenerator.getTestData().get(2).getTitle(),
                            eventDataGenerator.getTestData().get(2).getStartDate(),
                            eventDataGenerator.getTestData().get(2).getEndDate(),
                            eventDataGenerator.getTestData().get(2).getSeatPrice(),
                            eventDataGenerator.getTestData().get(2).getStandingPrice(),
                            eventDataGenerator.getTestData().get(2).getType()
                        ),
                        tuple(
                            eventDataGenerator.getTestData().get(6).getId(),
                            eventDataGenerator.getTestData().get(6).getTitle(),
                            eventDataGenerator.getTestData().get(6).getStartDate(),
                            eventDataGenerator.getTestData().get(6).getEndDate(),
                            eventDataGenerator.getTestData().get(6).getSeatPrice(),
                            eventDataGenerator.getTestData().get(6).getStandingPrice(),
                            eventDataGenerator.getTestData().get(6).getType()
                        ),
                        tuple(
                            eventDataGenerator.getTestData().get(10).getId(),
                            eventDataGenerator.getTestData().get(10).getTitle(),
                            eventDataGenerator.getTestData().get(10).getStartDate(),
                            eventDataGenerator.getTestData().get(10).getEndDate(),
                            eventDataGenerator.getTestData().get(10).getSeatPrice(),
                            eventDataGenerator.getTestData().get(10).getStandingPrice(),
                            eventDataGenerator.getTestData().get(10).getType()
                        )
                    )
            );
        });
    }

    @Test
    void getAllUsers_loggedInAsAdmin_successfullyGetsAllUsers() {
        assertDoesNotThrow(() -> {
            // Get the actual List of read News
            int page = 0; // Page number
            int size = 10; // Page size

            byte[] result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_USERS)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(admin1_info.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            PageDto<UserListDto> pageDto = objectMapper.readValue(result, new TypeReference<>() {
            });

            List<UserListDto> returnedEvents = pageDto.getContent();

            assertAll(
                () -> assertThat(returnedEvents).isNotNull(),
                () -> assertThat(returnedEvents.size()).isEqualTo(size),
                () -> assertThat(returnedEvents)
                    .extracting(
                        UserListDto::getId,
                        UserListDto::getFirstName,
                        UserListDto::getLastName,
                        UserListDto::getIsLocked,
                        UserListDto::getRole
                    ).contains(
                        tuple(
                            userDataGenerator.getTestData().get(0).getId(),
                            userDataGenerator.getTestData().get(0).getFirstName(),
                            userDataGenerator.getTestData().get(0).getLastName(),
                            userDataGenerator.getTestData().get(0).isLocked(),
                            userDataGenerator.getTestData().get(0).getRole()

                        ),
                        tuple(
                            userDataGenerator.getTestData().get(1).getId(),
                            userDataGenerator.getTestData().get(1).getFirstName(),
                            userDataGenerator.getTestData().get(1).getLastName(),
                            userDataGenerator.getTestData().get(1).isLocked(),
                            userDataGenerator.getTestData().get(1).getRole()

                        )
                    )
            );
        });
    }

    @Test
    void getAllArtists_loggedInAsAdmin_successfullyGetsAllArtists() {
        assertDoesNotThrow(() -> {
            byte[] result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_ARTISTS)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(admin1_info.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            var returnedArtistsIterator = objectMapper.readerFor(ArtistDetailDto.class).<ArtistDetailDto>readValues(result);

            // Check Iterator before using it
            assertThat(returnedArtistsIterator).isNotNull();

            var returnedArtists = new ArrayList<ArtistDetailDto>();
            returnedArtistsIterator.forEachRemaining(returnedArtists::add);

            assertAll(
                () -> assertThat(returnedArtists).isNotNull(),
                () -> assertThat(returnedArtists).hasSizeGreaterThan(70),
                () -> assertThat(returnedArtists)
                    .extracting(
                        ArtistDetailDto::getId,
                        ArtistDetailDto::getFirstname,
                        ArtistDetailDto::getLastname,
                        ArtistDetailDto::getFictionalName
                    ).contains(
                        tuple(
                            artistDataGenerator.getTestData().get(0).getId(),
                            artistDataGenerator.getTestData().get(0).getFirstname(),
                            artistDataGenerator.getTestData().get(0).getLastname(),
                            artistDataGenerator.getTestData().get(0).getFictionalName()
                        ),
                        tuple(
                            artistDataGenerator.getTestData().get(1).getId(),
                            artistDataGenerator.getTestData().get(1).getFirstname(),
                            artistDataGenerator.getTestData().get(1).getLastname(),
                            artistDataGenerator.getTestData().get(1).getFictionalName()
                        ),
                        tuple(
                            artistDataGenerator.getTestData().get(2).getId(),
                            artistDataGenerator.getTestData().get(2).getFirstname(),
                            artistDataGenerator.getTestData().get(2).getLastname(),
                            artistDataGenerator.getTestData().get(2).getFictionalName()
                        )
                    )
            );
        });
    }

    @Test
    void getAllHalls_loggedInAsAdmin_successfullyGetsAllHalls() {
        assertDoesNotThrow(() -> {
            byte[] result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_HALLS)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(admin1_info.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            var returnedHallsIterator = objectMapper.readerFor(HallDetailDto.class).<HallDetailDto>readValues(result);

            // Check Iterator before using it
            assertThat(returnedHallsIterator).isNotNull();

            var returnedHalls = new ArrayList<HallDetailDto>();
            returnedHallsIterator.forEachRemaining(returnedHalls::add);

            assertAll(
                () -> assertThat(returnedHalls).isNotNull(),
                () -> assertThat(returnedHalls).hasSizeGreaterThan(60),
                () -> assertThat(returnedHalls)
                    .extracting(
                        HallDetailDto::getId,
                        HallDetailDto::getName,
                        HallDetailDto::getStandingCount
                    ).contains(
                        tuple(
                            hallDataGenerator.getTestData().get(0).getId(),
                            hallDataGenerator.getTestData().get(0).getName(),
                            hallDataGenerator.getTestData().get(0).getStandingCount()
                        ),
                        tuple(
                            hallDataGenerator.getTestData().get(1).getId(),
                            hallDataGenerator.getTestData().get(1).getName(),
                            hallDataGenerator.getTestData().get(1).getStandingCount()
                        ),
                        tuple(
                            hallDataGenerator.getTestData().get(2).getId(),
                            hallDataGenerator.getTestData().get(2).getName(),
                            hallDataGenerator.getTestData().get(2).getStandingCount()
                        ),
                        tuple(
                            hallDataGenerator.getTestData().get(3).getId(),
                            hallDataGenerator.getTestData().get(3).getName(),
                            hallDataGenerator.getTestData().get(3).getStandingCount()
                        )
                    )
            );
        });
    }

    @Test
    void lockUser_loggedInAsAdmin_successfullyLocksUser() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(true)
            .id(user1_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(admin1_info.getEmail()).roles("ADMIN"))
            ).andExpectAll(
                status().isOk()
            );

            ApplicationUser savedUser = userRepository.findById(user1_info.getId()).orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(user1_info.getId())));
            assertTrue(savedUser.isLocked());
        });
    }

    @Test
    void lockOwnAccount_loggedInAsAdmin_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(true)
            .id(admin1_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(admin1_info.getEmail()).roles("ADMIN"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void lockUser_notLoggedIn_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(true)
            .id(user2_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(user1_info.getEmail()).roles("USER"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void lockUser_LoggedInAsNonAdmin_returnsUnauthorized() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(false)
            .id(user1_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS)
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
    void unlockUser_loggedInAsAdmin_successfullyUnlocksUser() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(false)
            .id(lockedUser_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            byte[] body = this.mockMvc.perform(patch(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(admin1_info.getEmail()).roles("ADMIN"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();


            // Get respository
            ApplicationUser savedUser = userRepository.findById(lockedUser_info.getId())
                .orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(lockedUser_info.getId())));

            // Get returned user-content
            UserDetailDto returnedUser = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).next();

            assertAll(
                () -> assertFalse(savedUser.isLocked()), // Verify repository

                () -> assertThat(returnedUser).extracting( // Check if UserDetailDto returns up to date information
                    UserDetailDto::getId,
                    UserDetailDto::getEmail,
                    UserDetailDto::getFirstName,
                    UserDetailDto::getLastName,
                    UserDetailDto::getIsLocked
                ).containsExactly(
                    savedUser.getId(),
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.isLocked()
                ),
                () -> assertThat(returnedUser.getLocation().getAddress()).isEqualTo(savedUser.getLocation().getAddress()),
                () -> assertThat(returnedUser.getLocation().getCity()).isEqualTo(savedUser.getLocation().getCity()),
                () -> assertThat(returnedUser.getLocation().getCountry()).isEqualTo(savedUser.getLocation().getCountry()),
                () -> assertThat(returnedUser.getLocation().getPostalCode()).isEqualTo(savedUser.getLocation().getPostalCode())
            );
        });
    }


    @Test
    void unlockUser_loggedInAsNonAdmin_returnsForbidden() {
        UserUpdateManagementDto toUpdate = UserUpdateManagementDto
            .builder()
            .isLocked(false)
            .id(lockedUser_info.getId())
            .build();

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(toUpdate)
                )
                .with(user(user1_info.getEmail()).roles("USER"))
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void createNonAdminUser_whileLoggedInAsAdmin_isCreated() {
        assertDoesNotThrow(() -> {

            byte[] content = this.mockMvc.perform(post(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDto_genericUser))
                .with(user(admin1_info.getEmail()).roles("ADMIN"))
            ).andExpect(
                status().isCreated()
            ).andReturn().getResponse().getContentAsByteArray();

            // Returned by the Endpoint
            UserDetailDto returnedDetailDto = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(content).next();

            // Repository entry
            ApplicationUser repositoryUser = userRepository.findUserByEmail(userCreateDto_genericUser.getEmail())
                .orElseThrow(() -> new NotFoundException("No user with id email found".formatted(userCreateDto_genericUser.getEmail())));


            assertAll(
                () -> assertNotNull(returnedDetailDto),
                () -> assertThat(returnedDetailDto) // verify returned DTO
                    .extracting(
                        UserDetailDto::getEmail,
                        UserDetailDto::getFirstName,
                        UserDetailDto::getLastName,
                        UserDetailDto::getLocation,
                        UserDetailDto::getIsLocked
                    )
                    .contains(
                        userCreateDto_genericUser.getEmail(),
                        userCreateDto_genericUser.getFirstName(),
                        userCreateDto_genericUser.getLastName(),
                        userCreateDto_genericUser.getLocation(),
                        userCreateDto_genericUser.getIsLocked()
                    ),
                () -> assertThat(repositoryUser) // verify repository entry
                    .extracting(
                        ApplicationUser::getEmail,
                        ApplicationUser::getFirstName,
                        ApplicationUser::getLastName,
                        ApplicationUser::isLocked,
                        ApplicationUser::getPassword,
                        ApplicationUser::getFailedAuths,
                        ApplicationUser::getRole
                    )
                    .contains(
                        userCreateDto_genericUser.getEmail(),
                        userCreateDto_genericUser.getFirstName(),
                        userCreateDto_genericUser.getLastName(),
                        userCreateDto_genericUser.getIsLocked(),
                        0,
                        UserRole.ROLE_USER
                    ),
                // verify password
                () -> passwordEncoder.matches(userCreateDto_genericUser.getPassword(), repositoryUser.getPassword()),
                // verify location
                () -> assertThat(repositoryUser.getLocation().getAddress()).isEqualTo(userCreateDto_genericUser.getLocation().getAddress()),
                () -> assertThat(repositoryUser.getLocation().getCity()).isEqualTo(userCreateDto_genericUser.getLocation().getCity()),
                () -> assertThat(repositoryUser.getLocation().getCountry()).isEqualTo(userCreateDto_genericUser.getLocation().getCountry()),
                () -> assertThat(repositoryUser.getLocation().getPostalCode()).isEqualTo(userCreateDto_genericUser.getLocation().getPostalCode())
            );
        });
    }

    @Test
    void createAdminUser_whileLoggedInAsAdmin_isCreatedWithAdminRole() {
        assertDoesNotThrow(() -> {

            byte[] content = this.mockMvc.perform(post(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDto_adminUser))
                .with(user(admin1_info.getEmail()).roles("ADMIN"))
            ).andExpect(
                status().isCreated()
            ).andReturn().getResponse().getContentAsByteArray();

            // Returned by the Endpoint
            UserDetailDto returnedDetailDto = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(content).next();

            // Repository entry
            ApplicationUser repositoryUser = userRepository.findUserByEmail(userCreateDto_adminUser.getEmail())
                .orElseThrow(() -> new NotFoundException("No user with id email found".formatted(userCreateDto_adminUser.getEmail())));


            assertAll(
                () -> assertNotNull(returnedDetailDto),
                () -> assertThat(returnedDetailDto) // verify returned DTO
                    .extracting(
                        UserDetailDto::getEmail,
                        UserDetailDto::getFirstName,
                        UserDetailDto::getLastName,
                        UserDetailDto::getLocation,
                        UserDetailDto::getIsLocked
                    )
                    .contains(
                        userCreateDto_adminUser.getEmail(),
                        userCreateDto_adminUser.getFirstName(),
                        userCreateDto_adminUser.getLastName(),
                        userCreateDto_adminUser.getLocation(),
                        userCreateDto_adminUser.getIsLocked()
                    ),
                () -> assertThat(repositoryUser) // verify repository entry
                    .extracting(
                        ApplicationUser::getEmail,
                        ApplicationUser::getFirstName,
                        ApplicationUser::getLastName,
                        ApplicationUser::isLocked,
                        ApplicationUser::getPassword,
                        ApplicationUser::getFailedAuths,
                        ApplicationUser::getRole
                    )
                    .contains(
                        userCreateDto_adminUser.getEmail(),
                        userCreateDto_adminUser.getFirstName(),
                        userCreateDto_adminUser.getLastName(),
                        userCreateDto_adminUser.getIsLocked(),
                        0,
                        UserRole.ROLE_ADMIN
                    ),
                // verify password
                () -> assertTrue(passwordEncoder.matches(userCreateDto_adminUser.getPassword(), repositoryUser.getPassword())),
                // verify location
                () -> assertThat(repositoryUser.getLocation().getAddress()).isEqualTo(userCreateDto_adminUser.getLocation().getAddress()),
                () -> assertThat(repositoryUser.getLocation().getCity()).isEqualTo(userCreateDto_adminUser.getLocation().getCity()),
                () -> assertThat(repositoryUser.getLocation().getCountry()).isEqualTo(userCreateDto_adminUser.getLocation().getCountry()),
                () -> assertThat(repositoryUser.getLocation().getPostalCode()).isEqualTo(userCreateDto_adminUser.getLocation().getPostalCode())
            );
        });
    }

    @Test
    void createAdminUser_whileLoggedInAsNonAdmin_returnsForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_USERS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDto_adminUser))
                .with(user(user1_info.getEmail()).roles("USER"))
            ).andExpect(
                status().isForbidden()
            );
        });
    }
}
