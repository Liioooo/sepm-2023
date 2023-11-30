package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateData")
@Component
public class ApplicationUserDataGenerator extends DataGenerator<ApplicationUser> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDataGenerator(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected List<ApplicationUser> generate() {
        if (applicationUserRepository.count() > 0) {
            LOGGER.info("user data already generated");
            return null;
        }
        LOGGER.info("generating users");

        final var users = List.of(
            ApplicationUser.builder()
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
                .build(),
            ApplicationUser.builder()
                .email("admin2@email.com")
                .firstName("Admin 2")
                .lastName("Admin 2")
                .password(passwordEncoder.encode("password"))
                .role(UserRole.ROLE_ADMIN)
                .location(UserLocation.builder()
                    .address("Brauhausgasse 2")
                    .postalCode("2320")
                    .city("Schwechat")
                    .country("Österreich")
                    .build())
                .build(),
            ApplicationUser.builder()
                .email("user1@email.com")
                .firstName("User")
                .lastName("user2")
                .password(passwordEncoder.encode("password"))
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Karlsplatz 13")
                    .postalCode("1040")
                    .city("Wien")
                    .country("Österreich")
                    .build())
                .build(),
            ApplicationUser.builder()
                .email("user2@email.com")
                .firstName("User")
                .lastName("user2")
                .password(passwordEncoder.encode("password"))
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Karlsplatz 13")
                    .postalCode("1040")
                    .city("Wien")
                    .country("Österreich")
                    .build())
                .build(),
            ApplicationUser.builder()
                .email("locked@email.com")
                .firstName("locked")
                .lastName("locked")
                .password(passwordEncoder.encode("password"))
                .isLocked(true)
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Karlsplatz 13")
                    .postalCode("1040")
                    .city("Wien")
                    .country("Österreich")
                    .build())
                .build(),
            ApplicationUser.builder()
                .email("toManyAttempts@email.com")
                .firstName("toManyAttempts")
                .lastName("toManyAttempts")
                .password(passwordEncoder.encode("password"))
                .failedAuths(6)
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Karlsplatz 13")
                    .postalCode("1040")
                    .city("Wien")
                    .country("Österreich")
                    .build())
                .build()
        );

        return applicationUserRepository.saveAll(users);
    }
}
