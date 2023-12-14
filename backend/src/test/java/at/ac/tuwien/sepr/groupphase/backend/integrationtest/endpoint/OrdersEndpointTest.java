package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RedeemReservationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
class OrdersEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @SpyBean
    private UserService userService;

    private final String API_BASE = "/api/v1/orders";

    private ApplicationUser user1;

    @BeforeEach()
    void setupData() {
        setupUser2();
    }

    private void setupUser2() {
        user1 = ApplicationUser.builder()
            .id(3L)
            .email("user1@email.com")
            .firstName("User")
            .lastName("user1")
            .role(UserRole.ROLE_USER)
            .location(UserLocation.builder()
                .address("Karlsplatz 13")
                .postalCode("1040")
                .city("Wien")
                .country("Österreich")
                .build())
            .build();
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 1092102})
    void getOrderById_whileNotLoggedIn_isForbidden(int id) {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void getOrderById_whileLoggedIn_returnsCorrectOrder() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isOk()
            );
        });
    }

    @Test
    void getOrderById_whileLoggedInAsNonOwner_isNotFound() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("user2@email.com").roles("USER"))
            ).andExpectAll(
                status().isNotFound()
            );
        });
    }

    @Test
    void getOrdersOfUser_whileNotLoggedIn_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    void getOrdersOfUser_whileLoggedIn_returnsCorrectOrder() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(get(API_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isOk()
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            List<OrderListDto> orders = Arrays.asList(objectMapper.readValue(content, OrderListDto[].class));

            assertThat(orders).containsExactlyInAnyOrder(
                orderMapper.orderToOrderListDto(Order.builder()
                    .id(1L)
                    .orderDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                    .orderType(OrderType.BUY)
                    .event(
                        Event.builder()
                            .id(1L)
                            .title("The Eras Tour")
                            .startDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                            .endDate(OffsetDateTime.of(2023, 12, 9, 23, 0, 0, 0, ZoneOffset.UTC))
                            .seatPrice(99.99f)
                            .standingPrice(69.99f)
                            .type(EventType.CONCERT)
                            .artist(
                                Artist.builder()
                                    .id(1L)
                                    .firstname("Taylor")
                                    .lastname("Swift")
                                    .build()
                            )
                            .hall(
                                Hall.builder()
                                    .name("Halle D")
                                    .id(1L)
                                    .location(
                                        Location.builder()
                                            .id(1L)
                                            .title("Wiener Stadthalle")
                                            .address("Roland-Rainer-Platz 1")
                                            .postalCode("1150")
                                            .city("Wien")
                                            .country("Österreich")
                                            .build()
                                    )
                                    .standingCount(500L)
                                    .build()
                            )
                            .build()
                    )
                    .build()),
                orderMapper.orderToOrderListDto(Order.builder()
                    .id(2L)
                    .orderDate(OffsetDateTime.of(2023, 10, 4, 11, 0, 0, 0, ZoneOffset.UTC))
                    .orderType(OrderType.BUY)
                    .event(
                        Event.builder()
                            .id(2L)
                            .title("Troubadour")
                            .startDate(OffsetDateTime.of(2023, 12, 10, 20, 0, 0, 0, ZoneOffset.UTC))
                            .endDate(OffsetDateTime.of(2023, 12, 10, 22, 0, 0, 0, ZoneOffset.UTC))
                            .seatPrice(79.99f)
                            .standingPrice(59.99f)
                            .type(EventType.CONCERT)
                            .artist(
                                Artist.builder()
                                    .id(2L)
                                    .firstname("Maren")
                                    .lastname("Morris")
                                    .build()
                            )
                            .hall(
                                Hall.builder()
                                    .id(2L)
                                    .name("Halle F")
                                    .location(
                                        Location.builder()
                                            .id(1L)
                                            .title("Wiener Stadthalle")
                                            .address("Roland-Rainer-Platz 1")
                                            .postalCode("1150")
                                            .city("Wien")
                                            .country("Österreich")
                                            .build()
                                    )
                                    .standingCount(200L)
                                    .build()
                            )
                            .build()
                    )
                    .build()),
                orderMapper.orderToOrderListDto(Order.builder()
                    .id(3L)
                    .orderDate(OffsetDateTime.of(2023, 12, 7, 10, 0, 0, 0, ZoneOffset.UTC))
                    .orderType(OrderType.BUY)
                    .event(
                        Event.builder()
                            .id(3L)
                            .title("Deutschland")
                            .startDate(OffsetDateTime.of(2024, 2, 11, 20, 0, 0, 0, ZoneOffset.UTC))
                            .endDate(OffsetDateTime.of(2024, 2, 11, 22, 30, 0, 0, ZoneOffset.UTC))
                            .seatPrice(180.00f)
                            .standingPrice(150.00f)
                            .type(EventType.CONCERT)
                            .artist(
                                Artist.builder()
                                    .id(3L)
                                    .fictionalName("Rammstein")
                                    .build()
                            )
                            .hall(
                                Hall.builder()
                                    .id(3L)
                                    .name("Halle Gasometer")
                                    .location(
                                        Location.builder()
                                            .id(2L)
                                            .title("Gasometer")
                                            .address("Guglgasse 6")
                                            .postalCode("1110")
                                            .city("Wien")
                                            .country("Österreich")
                                            .build()
                                    )
                                    .standingCount(1234L)
                                    .build()
                            )
                            .build()
                    )
                    .build()),
                orderMapper.orderToOrderListDto(Order.builder()
                    .id(4L)
                    .orderDate(OffsetDateTime.of(2023, 12, 8, 20, 15, 0, 0, ZoneOffset.UTC))
                    .orderType(OrderType.RESERVE)
                    .event(
                        Event.builder()
                            .id(4L)
                            .title("Blinding lights")
                            .startDate(OffsetDateTime.of(2024, 10, 12, 18, 0, 0, 0, ZoneOffset.UTC))
                            .endDate(OffsetDateTime.of(2024, 10, 12, 23, 30, 0, 0, ZoneOffset.UTC))
                            .seatPrice(200.00f)
                            .standingPrice(130.00f)
                            .type(EventType.CONCERT)
                            .artist(
                                Artist.builder()
                                    .id(4L)
                                    .fictionalName("The Weeknd")
                                    .build()
                            )
                            .hall(
                                Hall.builder()
                                    .id(4L)
                                    .name("Halle 1")
                                    .location(
                                        Location.builder()
                                            .id(3L)
                                            .title("Stadthalle Graz")
                                            .address("Messeplatz 1")
                                            .postalCode("8010")
                                            .city("Graz")
                                            .country("Österreich")
                                            .build()
                                    )
                                    .standingCount(200L)
                                    .build()
                            )
                            .build()
                    )
                    .build()),
                orderMapper.orderToOrderListDto(Order.builder()
                    .id(5L)
                    .orderDate(OffsetDateTime.of(2023, 8, 8, 4, 15, 0, 0, ZoneOffset.UTC))
                    .orderType(OrderType.RESERVE)
                    .event(
                        Event.builder()
                            .id(6L)
                            .title("leverage scalable infrastructures")
                            .startDate(OffsetDateTime.of(2024, 8, 3, 20, 15, 0, 0, ZoneOffset.UTC))
                            .endDate(OffsetDateTime.of(2024, 8, 3, 23, 0, 0, 0, ZoneOffset.UTC))
                            .seatPrice(132.19f)
                            .standingPrice(130.42f)
                            .type(EventType.SPORT)
                            .artist(
                                Artist.builder()
                                    .id(6L)
                                    .fictionalName("reintermediate rich applications")
                                    .build()
                            )
                            .hall(
                                Hall.builder()
                                    .id(3L)
                                    .name("Halle Gasometer")
                                    .location(
                                        Location.builder()
                                            .id(2L)
                                            .title("Gasometer")
                                            .address("Guglgasse 6")
                                            .postalCode("1110")
                                            .city("Wien")
                                            .country("Österreich")
                                            .build()
                                    )
                                    .standingCount(1234L)
                                    .build()
                            )
                            .build()
                    )
                    .build()
                )
            );
        });
    }

    @Test
    void redeemReservation_whileNotLoggedIn_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_BASE + "/" + 4)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new RedeemReservationDto(
                        new TicketCreateDto[] {new TicketCreateDto(TicketCategory.SEATING, 2L, 3L)}
                    ))
                )
            ).andExpectAll(
                status().isForbidden()
            );
        });
    }

    @Test
    @DirtiesContext
    void buyTickets_isSuccessful() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(API_BASE)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                            objectMapper.writeValueAsString(new OrderCreateDto(
                                    new TicketCreateDto[] { new TicketCreateDto(TicketCategory.SEATING, 4L, 1L) },
                                    4L,
                                    OrderType.BUY
                            ))
                    )
                    .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                    status().isCreated()
            );

            Mockito.doReturn(Optional.of(user1)).when(userService).getCurrentlyAuthenticatedUser();
            var order = orderService.getOrderById(11L);
            assertAll(
                    () -> assertThat(order.getTickets()).size().isEqualTo(1),
                    () -> assertThat(order.getTickets().get(0))
                            .extracting(Ticket::getTicketCategory, Ticket::getRowNumber, Ticket::getSeatNumber)
                            .containsExactly(TicketCategory.SEATING, 1L, 4L),
                    () -> assertThat(order.getOrderType()).isEqualTo(OrderType.BUY),
                    () -> assertThat(order.getEvent().getId()).isEqualTo(4L),
                    () -> assertThat(order.getOrderDate()).isNotNull(),
                    () -> assertThat(order.getReceipt()).isNotNull()
            );
        });
    }

    @Test
    @DirtiesContext
    void redeemReservation_withOneTicket_isSuccessful() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_BASE + "/" + 4)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new RedeemReservationDto(
                        new TicketCreateDto[] {new TicketCreateDto(TicketCategory.SEATING, 2L, 3L)}
                    ))
                )
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isNoContent()
            );

            Mockito.doReturn(Optional.of(user1)).when(userService).getCurrentlyAuthenticatedUser();
            var order = orderService.getOrderById(4L);
            assertAll(
                () -> assertThat(order.getTickets()).size().isEqualTo(1),
                () -> assertThat(order.getTickets().get(0))
                    .extracting(Ticket::getTicketCategory, Ticket::getRowNumber, Ticket::getSeatNumber, ticket -> ticket.getOrder().getId())
                    .containsExactly(TicketCategory.SEATING, 3L, 2L, 4L)
            );
        });
    }

    @Test
    @DirtiesContext
    void redeemReservation_withOneTicketNonOwner_isNotFound() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_BASE + "/" + 4)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new RedeemReservationDto(
                        new TicketCreateDto[] {new TicketCreateDto(TicketCategory.SEATING, 2L, 3L)}
                    ))
                )
                .with(user("user2@email.com").roles("USER"))
            ).andExpectAll(
                status().isNotFound()
            );
        });
    }
}
