package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Profile("generateTestData")
@Component
public class OrderDataGenerator extends DataGenerator<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApplicationUserDataGenerator userDataGenerator;
    private final EventDataGenerator eventDataGenerator;

    public OrderDataGenerator(ApplicationUserDataGenerator userDataGenerator, EventDataGenerator eventDataGenerator) {
        this.userDataGenerator = userDataGenerator;
        this.eventDataGenerator = eventDataGenerator;
    }

    @Override
    protected List<Order> generate() {
        final var orders = List.of(
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
        );

        LOGGER.info("generating orders");
        return orders;
    }
}
