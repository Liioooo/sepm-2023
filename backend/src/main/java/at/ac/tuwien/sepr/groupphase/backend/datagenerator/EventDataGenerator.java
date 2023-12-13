package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Profile("generateData")
@Component
public class EventDataGenerator extends DataGenerator<Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;
    private final ArtistDataGenerator artistDataGenerator;
    private final HallDataGenerator hallDataGenerator;

    public EventDataGenerator(
        EventRepository eventRepository,
        ArtistDataGenerator artistDataGenerator,
        HallDataGenerator hallDataGenerator
    ) {
        this.eventRepository = eventRepository;
        this.artistDataGenerator = artistDataGenerator;
        this.hallDataGenerator = hallDataGenerator;
    }

    @Override
    protected List<Event> generate() {
        if (eventRepository.count() > 0) {
            LOGGER.info("event data already generated");
            return null;
        }

        final var events = List.of(
            Event.builder()
                .title("The Eras Tour")
                .startDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2023, 12, 9, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(99.99f)
                .standingPrice(69.99f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(0))
                .hall(hallDataGenerator.getTestData().get(0))
                .build(),
            Event.builder()
                .title("Troubadour")
                .startDate(OffsetDateTime.of(2023, 12, 10, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2023, 12, 10, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(79.99f)
                .standingPrice(59.99f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(1))
                .build(),
            Event.builder()
                .title("Deutschland")
                .startDate(OffsetDateTime.of(2024, 2, 11, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 2, 11, 22, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(180.00f)
                .standingPrice(150.00f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(2))
                .hall(hallDataGenerator.getTestData().get(2))
                .build(),
            Event.builder()
                .title("Blinding lights")
                .startDate(OffsetDateTime.of(2024, 10, 12, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 12, 23, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(200.00f)
                .standingPrice(130.00f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(3))
                .hall(hallDataGenerator.getTestData().get(3))
                .build(),
            Event.builder()
                .title("파이팅!!!!!!")
                .startDate(OffsetDateTime.of(2024, 11, 13, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 13, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(30.00f)
                .standingPrice(20.00f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(4))
                .hall(hallDataGenerator.getTestData().get(4))
                .build(),
            Event.builder()
                .title("leverage scalable infrastructures")
                .startDate(OffsetDateTime.of(2024, 8, 3, 20, 15, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 8, 3, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(132.19f)
                .standingPrice(130.42f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(5))
                .hall(hallDataGenerator.getTestData().get(2))
                .build(),
            Event.builder()
                .title("grow enterprise convergence")
                .startDate(OffsetDateTime.of(2024, 2, 21, 9, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 2, 21, 14, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(110.91f)
                .standingPrice(125.15f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(58))
                .hall(hallDataGenerator.getTestData().get(24))
                .build(),
            Event.builder()
                .title("engineer vertical partnerships")
                .startDate(OffsetDateTime.of(2024, 10, 4, 11, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 4, 16, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(131.82f)
                .standingPrice(120.91f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(58))
                .hall(hallDataGenerator.getTestData().get(29))
                .build(),
            Event.builder()
                .title("revolutionize next-generation schemas")
                .startDate(OffsetDateTime.of(2024, 1, 16, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 1, 16, 22, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(149.35f)
                .standingPrice(229.66f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(58))
                .hall(hallDataGenerator.getTestData().get(27))
                .build(),
            Event.builder()
                .title("embrace rich solutions")
                .startDate(OffsetDateTime.of(2024, 9, 16, 10, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 9, 16, 14, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(68.1f)
                .standingPrice(180.6f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(30))
                .hall(hallDataGenerator.getTestData().get(7))
                .build(),
            Event.builder()
                .title("deploy strategic technologies")
                .startDate(OffsetDateTime.of(2024, 3, 8, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 3, 8, 20, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(119.21f)
                .standingPrice(146.65f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(47))
                .hall(hallDataGenerator.getTestData().get(28))
                .build(),
            Event.builder()
                .title("orchestrate virtual bandwidth")
                .startDate(OffsetDateTime.of(2024, 10, 22, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 22, 23, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(116.78f)
                .standingPrice(236.92f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(31))
                .hall(hallDataGenerator.getTestData().get(22))
                .build(),
            Event.builder()
                .title("recontextualize sticky bandwidth")
                .startDate(OffsetDateTime.of(2024, 5, 1, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 1, 23, 45, 0, 0, ZoneOffset.UTC))
                .seatPrice(133.26f)
                .standingPrice(166.78f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(2))
                .hall(hallDataGenerator.getTestData().get(4))
                .build(),
            Event.builder()
                .title("disintermediate next-generation experiences")
                .startDate(OffsetDateTime.of(2024, 1, 7, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 1, 7, 23, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(109.19f)
                .standingPrice(142.07f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(22))
                .hall(hallDataGenerator.getTestData().get(7))
                .build(),
            Event.builder()
                .title("drive distributed communities")
                .startDate(OffsetDateTime.of(2024, 6, 25, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 6, 25, 22, 15, 0, 0, ZoneOffset.UTC))
                .seatPrice(140.31f)
                .standingPrice(111.17f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(5))
                .hall(hallDataGenerator.getTestData().get(9))
                .build(),
            Event.builder()
                .title("optimize strategic content")
                .startDate(OffsetDateTime.of(2024, 11, 8, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 8, 22, 5, 0, 0, ZoneOffset.UTC))
                .seatPrice(93.85f)
                .standingPrice(247.95f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(6))
                .hall(hallDataGenerator.getTestData().get(28))
                .build(),
            Event.builder()
                .title("brand vertical e-tailers")
                .startDate(OffsetDateTime.of(2024, 11, 15, 17, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 15, 20, 50, 0, 0, ZoneOffset.UTC))
                .seatPrice(144.68f)
                .standingPrice(186.08f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(27))
                .hall(hallDataGenerator.getTestData().get(6))
                .build(),
            Event.builder()
                .title("strategize granular metrics")
                .startDate(OffsetDateTime.of(2024, 6, 21, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 6, 21, 23, 25, 0, 0, ZoneOffset.UTC))
                .seatPrice(63.06f)
                .standingPrice(195.04f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(19))
                .hall(hallDataGenerator.getTestData().get(6))
                .build(),
            Event.builder()
                .title("optimize plug-and-play architectures")
                .startDate(OffsetDateTime.of(2024, 12, 18, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 12, 18, 23, 20, 0, 0, ZoneOffset.UTC))
                .seatPrice(109.29f)
                .standingPrice(149.84f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(42))
                .hall(hallDataGenerator.getTestData().get(15))
                .build(),
            Event.builder()
                .title("cultivate seamless bandwidth")
                .startDate(OffsetDateTime.of(2024, 5, 12, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 12, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(88.44f)
                .standingPrice(111.51f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(38))
                .hall(hallDataGenerator.getTestData().get(25))
                .build(),
            Event.builder()
                .title("embrace 24/7 partnerships")
                .startDate(OffsetDateTime.of(2024, 10, 15, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 15, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(89.6f)
                .standingPrice(116.7f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(14))
                .hall(hallDataGenerator.getTestData().get(34))
                .build(),
            Event.builder()
                .title("architect wireless platforms")
                .startDate(OffsetDateTime.of(2024, 1, 9, 16, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 1, 9, 21, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(130.3f)
                .standingPrice(172.7f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(12))
                .hall(hallDataGenerator.getTestData().get(40))
                .build(),
            Event.builder()
                .title("target global web-readiness")
                .startDate(OffsetDateTime.of(2024, 7, 7, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 7, 7, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(60.31f)
                .standingPrice(166.55f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(29))
                .hall(hallDataGenerator.getTestData().get(43))
                .build(),
            Event.builder()
                .title("whiteboard front-end web services")
                .startDate(OffsetDateTime.of(2024, 9, 2, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 9, 2, 20, 55, 0, 0, ZoneOffset.UTC))
                .seatPrice(86.44f)
                .standingPrice(153.37f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(59))
                .hall(hallDataGenerator.getTestData().get(2))
                .build(),
            Event.builder()
                .title("mesh customized supply-chains")
                .startDate(OffsetDateTime.of(2024, 5, 10, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 10, 20, 55, 0, 0, ZoneOffset.UTC))
                .seatPrice(62.17f)
                .standingPrice(195.67f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(29))
                .hall(hallDataGenerator.getTestData().get(45))
                .build(),
            Event.builder()
                .title("disintermediate synergistic ROI")
                .startDate(OffsetDateTime.of(2024, 7, 24, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 7, 24, 20, 30, 0, 0, ZoneOffset.UTC))
                .seatPrice(68.34f)
                .standingPrice(102.23f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(33)).hall(hallDataGenerator.getTestData().get(1))
                .build(),
            Event.builder()
                .title("grow robust relationships")
                .startDate(OffsetDateTime.of(2024, 10, 12, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 12, 22, 15, 0, 0, ZoneOffset.UTC))
                .seatPrice(80.26f)
                .standingPrice(150.61f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(20))
                .build(),
            Event.builder()
                .title("morph front-end supply-chains")
                .startDate(OffsetDateTime.of(2024, 8, 17, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 8, 17, 21, 45, 0, 0, ZoneOffset.UTC))
                .seatPrice(126.89f)
                .standingPrice(225.59f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(26))
                .hall(hallDataGenerator.getTestData().get(17))
                .build(),
            Event.builder()
                .title("harness scalable platforms")
                .startDate(OffsetDateTime.of(2024, 8, 16, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 8, 16, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(128.73f)
                .standingPrice(242.79f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(60))
                .hall(hallDataGenerator.getTestData().get(5))
                .build(),
            Event.builder()
                .title("target granular initiatives")
                .startDate(OffsetDateTime.of(2024, 3, 4, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 3, 4, 20, 15, 0, 0, ZoneOffset.UTC))
                .seatPrice(103.41f)
                .standingPrice(186.69f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(41))
                .hall(hallDataGenerator.getTestData().get(8))
                .build(),
            Event.builder()
                .title("synthesize cutting-edge e-services")
                .startDate(OffsetDateTime.of(2024, 7, 19, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 7, 19, 22, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(100.47f)
                .standingPrice(228.57f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(6))
                .hall(hallDataGenerator.getTestData().get(13))
                .build(),
            Event.builder()
                .title("synergize frictionless content")
                .startDate(OffsetDateTime.of(2024, 6, 14, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 6, 14, 20, 45, 0, 0, ZoneOffset.UTC))
                .seatPrice(97.35f)
                .standingPrice(249.06f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(35))
                .hall(hallDataGenerator.getTestData().get(17))
                .build(),
            Event.builder()
                .title("productize revolutionary convergence")
                .startDate(OffsetDateTime.of(2024, 10, 16, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 16, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(145.21f)
                .standingPrice(131.02f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(9))
                .hall(hallDataGenerator.getTestData().get(11))
                .build(),
            Event.builder()
                .title("envisioneer sexy platforms")
                .startDate(OffsetDateTime.of(2024, 5, 21, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 21, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(80.79f)
                .standingPrice(140.0f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(40))
                .hall(hallDataGenerator.getTestData().get(15))
                .build(),
            Event.builder()
                .title("whiteboard B2B initiatives")
                .startDate(OffsetDateTime.of(2024, 4, 1, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 4, 1, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(61.47f)
                .standingPrice(230.7f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(32))
                .hall(hallDataGenerator.getTestData().get(0))
                .build(),
            Event.builder()
                .title("deploy integrated bandwidth")
                .startDate(OffsetDateTime.of(2024, 12, 22, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 12, 22, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(66.47f)
                .standingPrice(243.46f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(31))
                .build(),
            Event.builder()
                .title("recontextualize proactive systems")
                .startDate(OffsetDateTime.of(2024, 7, 8, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 7, 8, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(91.66f)
                .standingPrice(155.04f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(23))
                .build(),
            Event.builder()
                .title("facilitate dynamic convergence")
                .startDate(OffsetDateTime.of(2024, 11, 14, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 14, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(114.16f)
                .standingPrice(228.2f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(37))
                .hall(hallDataGenerator.getTestData().get(39))
                .build(),
            Event.builder()
                .title("synthesize granular networks")
                .startDate(OffsetDateTime.of(2024, 12, 16, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 12, 16, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(71.75f)
                .standingPrice(109.8f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(22))
                .hall(hallDataGenerator.getTestData().get(32))
                .build(),
            Event.builder()
                .title("deploy best-of-breed partnerships")
                .startDate(OffsetDateTime.of(2024, 12, 25, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 12, 25, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(83.89f)
                .standingPrice(159.73f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(15))
                .hall(hallDataGenerator.getTestData().get(50))
                .build(),
            Event.builder()
                .title("benchmark user-centric metrics")
                .startDate(OffsetDateTime.of(2024, 11, 19, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 19, 22, 12, 0, 0, ZoneOffset.UTC))
                .seatPrice(94.28f)
                .standingPrice(243.27f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(2))
                .hall(hallDataGenerator.getTestData().get(34))
                .build(),
            Event.builder()
                .title("redefine rich vortals")
                .startDate(OffsetDateTime.of(2024, 5, 8, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 8, 23, 10, 0, 0, ZoneOffset.UTC))
                .seatPrice(113.51f)
                .standingPrice(137.87f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(36))
                .hall(hallDataGenerator.getTestData().get(19))
                .build(),
            Event.builder()
                .title("grow viral models")
                .startDate(OffsetDateTime.of(2024, 11, 1, 17, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 11, 1, 20, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(110.78f)
                .standingPrice(227.3f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(21))
                .hall(hallDataGenerator.getTestData().get(37))
                .build(),
            Event.builder()
                .title("implement user-centric interfaces")
                .startDate(OffsetDateTime.of(2024, 4, 19, 15, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 4, 19, 20, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(141.49f)
                .standingPrice(171.37f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(10))
                .hall(hallDataGenerator.getTestData().get(18))
                .build(),
            Event.builder()
                .title("whiteboard revolutionary architectures")
                .startDate(OffsetDateTime.of(2024, 6, 9, 10, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 6, 9, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(130.95f)
                .standingPrice(188.42f)
                .type(EventType.SHOW)
                .artist(artistDataGenerator.getTestData().get(58))
                .hall(hallDataGenerator.getTestData().get(37))
                .build(),
            Event.builder()
                .title("engineer plug-and-play methodologies")
                .startDate(OffsetDateTime.of(2024, 8, 25, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 8, 25, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(72.22f)
                .standingPrice(169.42f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(48))
                .hall(hallDataGenerator.getTestData().get(10))
                .build(),
            Event.builder()
                .title("synthesize rich deliverables")
                .startDate(OffsetDateTime.of(2024, 6, 9, 17, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 6, 9, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(69.35f)
                .standingPrice(224.61f)
                .type(EventType.CULTURE)
                .artist(artistDataGenerator.getTestData().get(35))
                .hall(hallDataGenerator.getTestData().get(28))
                .build(),
            Event.builder()
                .title("benchmark collaborative e-business")
                .startDate(OffsetDateTime.of(2024, 4, 24, 10, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 4, 24, 22, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(141.92f)
                .standingPrice(112.0f)
                .type(EventType.FESTIVAL)
                .artist(artistDataGenerator.getTestData().get(39))
                .hall(hallDataGenerator.getTestData().get(37))
                .build(),
            Event.builder()
                .title("e-enable vertical portals")
                .startDate(OffsetDateTime.of(2024, 5, 17, 10, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 17, 20, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(114.16f)
                .standingPrice(220.6f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(1))
                .hall(hallDataGenerator.getTestData().get(34))
                .build(),
            Event.builder()
                .title("deliver end-to-end e-services")
                .startDate(OffsetDateTime.of(2024, 3, 18, 17, 30, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 3, 18, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(109.15f)
                .standingPrice(167.74f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(22))
                .hall(hallDataGenerator.getTestData().get(35))
                .build(),
            Event.builder()
                .title("disintermediate e-business deliverables")
                .startDate(OffsetDateTime.of(2024, 5, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 5, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(81.44f)
                .standingPrice(122.81f)
                .type(EventType.SPORTS)
                .artist(artistDataGenerator.getTestData().get(43))
                .hall(hallDataGenerator.getTestData().get(41))
                .build(),
            Event.builder()
                .title("harness robust infrastructures")
                .startDate(OffsetDateTime.of(2024, 10, 17, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 10, 17, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(104.75f)
                .standingPrice(135.86f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(27)).hall(hallDataGenerator.getTestData().get(4))
                .build(),
            Event.builder()
                .title("empower user-centric applications")
                .startDate(OffsetDateTime.of(2024, 4, 6, 9, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 4, 6, 21, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(127.99f)
                .standingPrice(117.14f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(17))
                .hall(hallDataGenerator.getTestData().get(36))
                .build(),
            Event.builder()
                .title("embrace virtual web services")
                .startDate(OffsetDateTime.of(2024, 4, 17, 18, 0, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 4, 17, 23, 0, 0, 0, ZoneOffset.UTC))
                .seatPrice(140.91f)
                .standingPrice(146.29f)
                .type(EventType.CONCERT)
                .artist(artistDataGenerator.getTestData().get(54))
                .hall(hallDataGenerator.getTestData().get(46))
                .build(),
            Event.builder()
                .title("facilitate value-added channels")
                .startDate(OffsetDateTime.of(2024, 12, 11, 19, 30, 0, 0, ZoneOffset.UTC))
                .endDate(OffsetDateTime.of(2024, 12, 11, 23, 45, 0, 0, ZoneOffset.UTC))
                .seatPrice(138.77f)
                .standingPrice(238.8f)
                .type(EventType.UNKNOWN)
                .artist(artistDataGenerator.getTestData().get(7))
                .hall(hallDataGenerator.getTestData().get(4))
                .build()
        );

        LOGGER.info("generating events");
        return eventRepository.saveAll(events);
    }
}
