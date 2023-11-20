package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class ApplicationUserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDataGenerator(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void generateUsers() {
        final ApplicationUser adminUser = ApplicationUser.builder()
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

        final ApplicationUser normalUser1 = ApplicationUser.builder()
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
            .build();

        final ApplicationUser normalUser2 = ApplicationUser.builder()
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
            .build();

        final ApplicationUser lockedUser = ApplicationUser.builder()
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
            .build();

        final ApplicationUser lockedToManyAttemptsUser = ApplicationUser.builder()
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
            .build();

        if (!applicationUserRepository.findAll().isEmpty()) {
            LOGGER.info("user data already generated");
        } else {
            LOGGER.info("generating users");
            applicationUserRepository.save(adminUser);
            applicationUserRepository.save(normalUser1);
            applicationUserRepository.save(normalUser2);
            applicationUserRepository.save(lockedUser);
            applicationUserRepository.save(lockedToManyAttemptsUser);
        }
    }

    public void clearUsers() {
        LOGGER.info("clearing users");
        applicationUserRepository.deleteAll();
    }

}
