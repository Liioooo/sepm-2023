package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.OrderDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderUpdateTicketsDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RedeemReservationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketOrderUpdateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
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

    @Autowired
    private OrderDataGenerator orderDataGenerator;

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

            List<OrderListDto> expected = Stream.of(
                orderMapper.orderToOrderListDto(orderDataGenerator.getTestData().get(0)),
                orderMapper.orderToOrderListDto(orderDataGenerator.getTestData().get(1)),
                orderMapper.orderToOrderListDto(orderDataGenerator.getTestData().get(2)),
                orderMapper.orderToOrderListDto(orderDataGenerator.getTestData().get(3)),
                orderMapper.orderToOrderListDto(orderDataGenerator.getTestData().get(4))
            ).peek(orderListDto -> {
                if (orderListDto.getCancellationReceipts() == null) {
                    orderListDto.setCancellationReceipts(List.of());
                }
            }).toList();

            assertThat(orders).containsExactlyInAnyOrder(expected.toArray(OrderListDto[]::new));
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
                        new TicketCreateDto[] {new TicketCreateDto(TicketCategory.SEATING, 4L, 1L)},
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

    @Test
    @DirtiesContext
    void editOrder_whileLoggedInAsOwnerRemoveOneTicket_isSuccessful() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_BASE + "/" + 4 + "/tickets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new OrderUpdateTicketsDto(
                        List.of(
                            new TicketOrderUpdateDto(17L),
                            new TicketOrderUpdateDto(18L),
                            new TicketOrderUpdateDto(21L)
                        )
                    ))
                )
                .with(user("user1@email.com").roles("USER"))
            ).andExpectAll(
                status().isNoContent()
            );

            Mockito.doReturn(Optional.of(user1)).when(userService).getCurrentlyAuthenticatedUser();
            var order = orderService.getOrderById(4L);
            assertAll(
                () -> assertThat(order.getTickets()).size().isEqualTo(3),
                () -> assertThat(order.getTickets())
                    .extracting(Ticket::getId, Ticket::getTicketCategory, Ticket::getRowNumber, Ticket::getSeatNumber, ticket -> ticket.getOrder().getId())
                    .containsExactlyInAnyOrder(
                        tuple(17L, TicketCategory.SEATING, 3L, 2L, 4L),
                        tuple(18L, TicketCategory.SEATING, 3L, 3L, 4L),
                        tuple(21L, TicketCategory.STANDING, null, null, 4L)
                    )
            );
        });
    }

    @Test
    @DirtiesContext
    void editOrder_whileNotLoggedInAsNonOwner_isNotFound() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(patch(API_BASE + "/" + 4 + "/tickets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(new OrderUpdateTicketsDto(
                        List.of(
                            new TicketOrderUpdateDto(17L)
                        )
                    ))
                )
                .with(user("user2@email.com").roles("USER"))
            ).andExpectAll(
                status().isNotFound()
            );
        });
    }
}
