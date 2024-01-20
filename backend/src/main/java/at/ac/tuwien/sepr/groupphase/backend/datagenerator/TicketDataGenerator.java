package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import at.ac.tuwien.sepr.groupphase.backend.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Profile("generateData")
@Component
public class TicketDataGenerator extends DataGenerator<Ticket> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TicketRepository ticketRepository;
    private final OrderDataGenerator orderDataGenerator;
    private final Faker faker;

    public TicketDataGenerator(TicketRepository ticketRepository, OrderDataGenerator orderDataGenerator) {
        this.ticketRepository = ticketRepository;
        this.orderDataGenerator = orderDataGenerator;
        this.faker = new Faker();
    }

    @Override
    protected List<Ticket> generate() {
        if (ticketRepository.count() > 0) {
            LOGGER.info("Ticket data already generated");
            return null;
        }

        List<Ticket> tickets = new ArrayList<>(List.of(
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
        ));

        List<Ticket> autoGeneratedTickets = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            autoGeneratedTickets.add(Ticket.builder()
                .order(orderDataGenerator.getTestData().get(faker.number().numberBetween(100, 999)))
                .rowNumber(faker.number().numberBetween(1L, 20L))
                .seatNumber(faker.number().numberBetween(1L, 100L))
                .ticketCategory(TicketCategory.SEATING)
                .build());
        }
        tickets.addAll(autoGeneratedTickets);
        LOGGER.info("generating Tickets");
        return ticketRepository.saveAll(tickets);
    }
}
