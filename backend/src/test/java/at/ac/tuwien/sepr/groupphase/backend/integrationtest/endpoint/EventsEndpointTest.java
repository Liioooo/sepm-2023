package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.EventDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ArtistDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallRowsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class EventsEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventDataGenerator eventDataGenerator;

    @Autowired
    private ApplicationUserDataGenerator userDataGenerator;

    @Autowired
    private EventRepository eventRepository;

    private final String API_BASE = "/api/v1/events";

    private ApplicationUser user1;
    private ApplicationUser admin1;
    private Event event1;

    @BeforeEach()
    void setupData() {
        setupUsers();
        setupEvent();
    }

    private void setupUsers() {
        admin1 = userDataGenerator.getTestData().get(0);
        user1 = userDataGenerator.getTestData().get(4);
    }

    private void setupEvent() {
        event1 = eventDataGenerator.getTestData().get(0);
    }

    @Test
    void searchEvents_bySearch_whileLoggedIn_returnsCorrectEvents() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(get(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("user1@email.com").roles("USER"))
                .param("search", "blind")
                .param("page", "0")
                .param("size", "10")
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            PageDto<EventListDto> events = objectMapper.readValue(content, new TypeReference<PageDto<EventListDto>>() {
            });

            assertThat(events.getContent()).containsExactlyInAnyOrder(
                eventMapper.eventToEventListDto(eventDataGenerator.getTestData().get(3))
            );
        });
    }

    @Test
    void createEvent_whileLoggedInAsAdmin_createsEvent() {
        EventCreateDto toCreate = new EventCreateDto(
            "create-test-event-title",
            OffsetDateTime.of(2023, 10, 25, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2023, 10, 26, 0, 0, 0, 0, ZoneOffset.UTC),
            10F,
            5F,
            1L,
            1L,
            null,
            EventType.SHOW
        );

        MockMultipartFile imageFile = new MockMultipartFile(
            "image",                   // parameter name matching the EventCreateDto field
            "image.jpg",               // original filename
            MediaType.IMAGE_JPEG_VALUE, // media type
            "image content".getBytes() // content as byte array (replace with your image content)
        );

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(MockMvcRequestBuilders.multipart(API_BASE)
                .file(imageFile) // Attach the image file
                .param("title", toCreate.getTitle())
                .param("startDate", toCreate.getStartDate().toString())
                .param("endDate", toCreate.getEndDate().toString())
                .param("seatPrice", toCreate.getSeatPrice().toString())
                .param("standingPrice", toCreate.getStandingPrice().toString())
                .param("hallId", toCreate.getHallId().toString())
                .param("artistId", toCreate.getArtistId().toString())
                .param("type", toCreate.getType().toString())
                .with(user(admin1.getUsername()).roles("ADMIN"))
            ).andExpect(
                status().isCreated()
            ).andReturn().getResponse().getContentAsByteArray();

            // Check if newly created News-Article in a Database
            Event selectedEvent = eventRepository.findByTitle("create-test-event-title");

            assertAll(
                () -> assertNotNull(selectedEvent),
                () -> assertThat(selectedEvent)
                    .extracting(
                        Event::getTitle,
                        Event::getStartDate,
                        Event::getEndDate,
                        Event::getSeatPrice,
                        Event::getStandingPrice,
                        Event::getType
                    ).contains(
                        toCreate.getTitle(),
                        toCreate.getStartDate(),
                        toCreate.getEndDate(),
                        toCreate.getSeatPrice(),
                        toCreate.getStandingPrice(),
                        toCreate.getType()
                    ),
                () -> assertThat(selectedEvent.getHall().getId()).isEqualTo(toCreate.getHallId()),
                () -> assertThat(selectedEvent.getArtist().getId()).isEqualTo(toCreate.getArtistId())
            );
        });
    }

    @Test
    void getSingleEvent_whileLoggedInAsUser_returnsEvent() {
        assertDoesNotThrow(() -> {
            // Get Event 1
            var result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_BASE + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(user1.getUsername()).roles("USER"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsByteArray();

            EventDetailDto event = objectMapper.readerFor(EventDetailDto.class).<EventDetailDto>readValues(result).next();

            assertAll(
                () -> assertThat(event).isNotNull(),
                () -> assertThat(event)
                    .extracting(
                        EventDetailDto::getId,
                        EventDetailDto::getStartDate,
                        EventDetailDto::getEndDate,
                        EventDetailDto::getSeatPrice,
                        EventDetailDto::getStandingPrice,
                        EventDetailDto::getType,
                        EventDetailDto::getOccupiedSeats,
                        EventDetailDto::getOccupiedStandings
                    ).containsExactly(
                        event1.getId(),
                        event1.getStartDate(),
                        event1.getEndDate(),
                        event1.getSeatPrice(),
                        event1.getStandingPrice(),
                        event1.getType(),
                        eventService.getOccupiedSeats(event1.getId()),
                        eventService.getOccupiedStandings(event1.getId())
                    ),
                () -> assertNotNull(event.getArtist()),
                () -> assertThat(event.getArtist())
                    .extracting(
                        ArtistDetailDto::getId,
                        ArtistDetailDto::getFirstname,
                        ArtistDetailDto::getLastname,
                        ArtistDetailDto::getFictionalName
                    ).containsExactly(
                        event1.getArtist().getId(),
                        event1.getArtist().getFirstname(),
                        event1.getArtist().getLastname(),
                        event1.getArtist().getFictionalName()
                    ),
                () -> assertNotNull(event.getHall()),
                () -> assertThat(event.getHall())
                    .extracting(
                        HallRowsDetailDto::getId,
                        HallRowsDetailDto::getName,
                        HallRowsDetailDto::getStandingCount
                    ).containsExactly(
                        event1.getHall().getId(),
                        event1.getHall().getName(),
                        event1.getHall().getStandingCount()
                    ),
                () -> assertNotNull(event.getHall().getLocation()),
                () -> assertThat(event.getHall().getLocation())
                    .extracting(
                        LocationDetailDto::getId,
                        LocationDetailDto::getTitle,
                        LocationDetailDto::getAddress,
                        LocationDetailDto::getPostalCode,
                        LocationDetailDto::getCity,
                        LocationDetailDto::getCountry
                    ).containsExactly(
                        event1.getHall().getLocation().getId(),
                        event1.getHall().getLocation().getTitle(),
                        event1.getHall().getLocation().getAddress(),
                        event1.getHall().getLocation().getPostalCode(),
                        event1.getHall().getLocation().getCity(),
                        event1.getHall().getLocation().getCountry()
                    )
            );
        });
    }

}
