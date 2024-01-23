package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ArtistDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.HallDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.TicketDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class TicketEndpointTest {

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
    TicketDataGenerator ticketDataGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String API_BASE = "/api/v1/tickets";


    private ApplicationUser admin1;

    @BeforeEach()
    void setupData() {
        initUser();
    }

    void initUser() {
        admin1 = userDataGenerator.getTestData().get(0);
    }

    @Test
    void getTicketByUUD_loggedInAsUser_sucessfullyReturnsTicket() {
        Ticket verifyTicket = ticketDataGenerator.getTestData().get(0);

        assertDoesNotThrow(() -> {
            byte[] result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_BASE + "/verify/" + verifyTicket.getUuid().toString())
                .accept(MediaType.APPLICATION_JSON)
                .with(user(admin1.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            TicketListDto returnedTicket = objectMapper.readerFor(TicketListDto.class).<TicketListDto>readValues(result).next();


            assertAll(
                () -> assertThat(returnedTicket).isNotNull(),
                () -> assertThat(returnedTicket)
                    .extracting(
                        TicketListDto::getId,
                        TicketListDto::getTicketCategory,
                        TicketListDto::getRowNumber,
                        TicketListDto::getSeatNumber,
                        TicketListDto::getUuid

                    ).contains(
                        verifyTicket.getId(),
                        verifyTicket.getTicketCategory(),
                        verifyTicket.getRowNumber(),
                        verifyTicket.getSeatNumber(),
                        verifyTicket.getUuid()
                    )
            );
        });
    }

}
