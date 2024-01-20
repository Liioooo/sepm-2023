package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile("generateData")
@Component
public class ApplicationUserDataGenerator extends DataGenerator<ApplicationUser> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public ApplicationUserDataGenerator(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();

    }

    @Override
    protected List<ApplicationUser> generate() {
        if (applicationUserRepository.count() > 0) {
            LOGGER.info("user data already generated");
            return null;
        }
        LOGGER.info("generating users");

        List<ApplicationUser> users = new ArrayList<>(List.of(
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
                .lastName("user1")
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
                .email("lisa@email.com")
                .firstName("Lisa")
                .lastName("Marchl")
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
                .email("Seli@email.com")
                .firstName("Seli")
                .lastName("Köchl")
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
                .email("michele@email.com")
                .firstName("Michele")
                .lastName("Koch")
                .password(passwordEncoder.encode("password"))
                .isLocked(true)
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Mürzstraße 13")
                    .postalCode("1040")
                    .city("Frnakfurt")
                    .country("Deutschland")
                    .build())
                .build(),
            ApplicationUser.builder()
                .email("seb@email.com")
                .firstName("Sebastian")
                .lastName("Hasenhüttel")
                .password(passwordEncoder.encode("password"))
                .isLocked(true)
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address("Grazstraße 13")
                    .postalCode("8063")
                    .city("Graz-Umgebung")
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
        ));

        List<ApplicationUser> autoGeneratedUser = new ArrayList<>();
        List<String> uniqueEmails = new ArrayList<>();
        int count = 0;
       while(count < 1000) {
           String uniqueEmail;
           uniqueEmail = faker.internet().emailAddress();
           while (!uniqueEmails.contains(uniqueEmail)){
               uniqueEmails.add(uniqueEmail);
               count++;
           }
       }
        for (int i = 0; i < 1000; i++) {
            autoGeneratedUser.add(ApplicationUser.builder()
                .email(uniqueEmails.get(i))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .password(passwordEncoder.encode("password"))
                .failedAuths(faker.number().numberBetween(0, 6))
                .role(UserRole.ROLE_USER)
                .location(UserLocation.builder()
                    .address(faker.address().streetAddressNumber())
                    .postalCode(faker.address().zipCode())
                    .city(faker.address().city())
                    .country(faker.address().country())
                    .build())
                .build());
        }
        users.addAll(autoGeneratedUser);
        return applicationUserRepository.saveAll(users);
    }
}
