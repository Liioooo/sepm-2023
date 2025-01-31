package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateTestData")
@Component
public class TicketDataGenerator extends DataGenerator<Ticket> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderDataGenerator orderDataGenerator;

    public TicketDataGenerator(OrderDataGenerator orderDataGenerator) {
        this.orderDataGenerator = orderDataGenerator;
    }

    @Override
    protected List<Ticket> generate() {
        final var tickets = List.of(
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(0))
                .rowNumber(1L)
                .seatNumber(1L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(0))
                .rowNumber(1L)
                .seatNumber(2L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(0))
                .rowNumber(1L)
                .seatNumber(3L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(0))
                .rowNumber(2L)
                .seatNumber(1L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(1))
                .rowNumber(1L)
                .seatNumber(1L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(1))
                .rowNumber(1L)
                .seatNumber(2L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(1)).ticketCategory(TicketCategory.STANDING).build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(2))
                .rowNumber(1L)
                .seatNumber(1L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(2))
                .rowNumber(1L)
                .seatNumber(2L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(2))
                .rowNumber(1L)
                .seatNumber(3L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(2))
                .rowNumber(1L)
                .seatNumber(4L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(3))
                .rowNumber(3L)
                .seatNumber(2L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(3))
                .rowNumber(3L)
                .seatNumber(3L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(3))
                .rowNumber(3L)
                .seatNumber(4L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(3))
                .rowNumber(3L)
                .seatNumber(5L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(3)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(3)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(3)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(3)).ticketCategory(TicketCategory.STANDING).build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(4))
                .rowNumber(2L)
                .seatNumber(9L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(4))
                .rowNumber(2L)
                .seatNumber(10L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(5))
                .rowNumber(2L)
                .seatNumber(9L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(5))
                .rowNumber(2L)
                .seatNumber(10L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(6))
                .rowNumber(2L)
                .seatNumber(9L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(6))
                .rowNumber(2L)
                .seatNumber(10L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(7))
                .rowNumber(2L)
                .seatNumber(9L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(7))
                .rowNumber(2L)
                .seatNumber(10L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(8))
                .rowNumber(2L)
                .seatNumber(9L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),
            Ticket.builder()
                .order(orderDataGenerator.getTestData().get(8))
                .rowNumber(2L)
                .seatNumber(10L)
                .ticketCategory(TicketCategory.SEATING)
                .build(),

            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build(),
            Ticket.builder().order(orderDataGenerator.getTestData().get(9)).ticketCategory(TicketCategory.STANDING).build()
        );

        LOGGER.info("generating Tickets");
        return tickets;
    }
}
