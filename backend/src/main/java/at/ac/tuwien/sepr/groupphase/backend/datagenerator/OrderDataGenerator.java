package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import at.ac.tuwien.sepr.groupphase.backend.repository.OrderRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Profile("generateData")
@Component
public class OrderDataGenerator extends DataGenerator<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderRepository orderRepository;
    private final ApplicationUserDataGenerator userDataGenerator;
    private final EventDataGenerator eventDataGenerator;
    private final Faker faker;
    private final Environment environment;

    public OrderDataGenerator(OrderRepository orderRepository, ApplicationUserDataGenerator userDataGenerator, EventDataGenerator eventDataGenerator, Environment environment) {
        this.orderRepository = orderRepository;
        this.userDataGenerator = userDataGenerator;
        this.eventDataGenerator = eventDataGenerator;
        this.environment = environment;
        this.faker = new Faker();

    }

    @Override
    protected List<Order> generate() {
        if (orderRepository.count() > 0) {
            LOGGER.info("order data already generated");
            return null;
        }
        List<Order> orders = new ArrayList<>(List.of(
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(2))
                .event(eventDataGenerator.getTestData().get(0))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 10, 4, 11, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(2))
                .event(eventDataGenerator.getTestData().get(1))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 12, 7, 10, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(2))
                .event(eventDataGenerator.getTestData().get(2))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 12, 8, 20, 15, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.RESERVE)
                .user(userDataGenerator.getTestData().get(2))
                .event(eventDataGenerator.getTestData().get(3))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 8, 8, 4, 15, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.RESERVE)
                .user(userDataGenerator.getTestData().get(2))
                .event(eventDataGenerator.getTestData().get(5))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 10, 2, 20, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(3))
                .event(eventDataGenerator.getTestData().get(0))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 1, 4, 11, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(3))
                .event(eventDataGenerator.getTestData().get(1))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 7, 10, 10, 0, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(3))
                .event(eventDataGenerator.getTestData().get(2))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 12, 13, 10, 15, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.RESERVE)
                .user(userDataGenerator.getTestData().get(3))
                .event(eventDataGenerator.getTestData().get(3))
                .build(),
            Order.builder()
                .orderDate(OffsetDateTime.of(2023, 8, 1, 5, 15, 0, 0, ZoneOffset.UTC))
                .orderType(OrderType.RESERVE)
                .user(userDataGenerator.getTestData().get(3))
                .event(eventDataGenerator.getTestData().get(5))
                .build()
        ));

        if (environment.acceptsProfiles(Profiles.of("test"))) {
            return orderRepository.saveAll(orders);
        }

        List<Order> autoGeneratedOrders = new ArrayList<>();
        final var minLocalDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        final var maxLocalDate = LocalDateTime.of(2024, 12, 31, 0, 0);

        for (int i = 0; i < 1000; i++) {
            final var orderDate = generateDateBetween(minLocalDate, maxLocalDate);

            autoGeneratedOrders.add(Order.builder()
                .orderDate(OffsetDateTime.from(orderDate))
                .orderType(OrderType.BUY)
                .user(userDataGenerator.getTestData().get(faker.number().numberBetween(100, 999)))
                .event(eventDataGenerator.getTestData().get(faker.number().numberBetween(100, 999)))
                .build()
            );
        }

        orders.addAll(autoGeneratedOrders);

        LOGGER.info("generating orders");
        return orderRepository.saveAll(orders);
    }

    private OffsetDateTime generateDateBetween(LocalDateTime min, LocalDateTime max) {
        final var minDate = Date.from(min.toInstant(ZoneOffset.UTC));
        final var maxDate = Date.from(max.toInstant(ZoneOffset.UTC));
        final var fakerValue = faker.date().between(minDate, maxDate);

        return OffsetDateTime.ofInstant(fakerValue.toInstant(), ZoneOffset.UTC);
    }
}
