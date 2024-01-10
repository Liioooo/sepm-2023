package at.ac.tuwien.sepr.groupphase.backend.unittest.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RowListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import at.ac.tuwien.sepr.groupphase.backend.entity.Row;
import at.ac.tuwien.sepr.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.LocationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.HallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles({"test", "generateTestData"})
@DirtiesContext(classMode = AFTER_CLASS)
public class HallServiceTest {
    @Autowired
    private HallService hallService;

    @MockBean
    private HallRepository hallRepository;

    @MockBean
    private LocationRepository locationRepository;

    @BeforeEach
    void setup() {
        Mockito.when(hallRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(locationRepository.findById(1L)).thenReturn(Optional.of(new Location(1L, "Testlocation", "Teststreet", "1012", "Wien", "Österreich", List.of())));
    }

    @Test
    void createHall_withCorrectData_returnsCorrectHallData() {
        HallCreateDto hallCreateDto = new HallCreateDto(
            "Test hall",
            20L,
            1L,
            new RowListDto[] {
                new RowListDto(1L, 20L),
                new RowListDto(2L, 20L),
                new RowListDto(3L, 18L),
                new RowListDto(4L, 16L)
            }
        );
        Hall hall = hallService.createHall(hallCreateDto);

        assertAll(
            () -> assertNotNull(hall),
            () -> assertNotNull(hall.getLocation()),
            () -> assertNotNull(hall.getRows()),
            () -> assertThat(hall.getRows()).hasSize(4),
            () -> assertThat(hall.getRows())
                .extracting(Row::getNumber, Row::getNumberOfSeats)
                .containsExactlyInAnyOrder(
                    tuple(1L, 20L),
                    tuple(2L, 20L),
                    tuple(3L, 18L),
                    tuple(4L, 16L)
                ),
            () -> assertThat(hall.getLocation())
                .extracting(Location::getTitle, Location::getAddress, Location::getPostalCode, Location::getCity, Location::getCountry)
                .contains("Testlocation", "Teststreet", "1012", "Wien", "Österreich"),
            () -> assertThat(hall)
                .extracting(Hall::getName, Hall::getStandingCount)
                .contains("Test hall", 20L)
        );
    }

    @Test
    void createHall_withoutAnyRowsCorrectData_returnsCorrectHallData() {
        HallCreateDto hallCreateDto = new HallCreateDto(
            "Test hall",
            20L,
            1L,
            new RowListDto[] {}
        );
        Hall hall = hallService.createHall(hallCreateDto);

        assertAll(
            () -> assertNotNull(hall),
            () -> assertNotNull(hall.getLocation()),
            () -> assertNotNull(hall.getRows()),
            () -> assertThat(hall.getRows()).hasSize(0),
            () -> assertThat(hall.getLocation())
                .extracting(Location::getTitle, Location::getAddress, Location::getPostalCode, Location::getCity, Location::getCountry)
                .contains("Testlocation", "Teststreet", "1012", "Wien", "Österreich"),
            () -> assertThat(hall)
                .extracting(Hall::getName, Hall::getStandingCount)
                .contains("Test hall", 20L)
        );
    }
}
