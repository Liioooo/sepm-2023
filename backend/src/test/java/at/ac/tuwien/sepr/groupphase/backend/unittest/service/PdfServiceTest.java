package at.ac.tuwien.sepr.groupphase.backend.unittest.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
public class PdfServiceTest {

    @Autowired
    private PdfService pdfService;

    private Order order;

    private Event event;

    private ApplicationUser user;

    @BeforeEach
    void init() {
        initUser();
        initOrder();
        initEvent();
    }

    void initUser() {
        user = ApplicationUser.builder()
            .email("admin@email.com")
            .firstName("Admin")
            .lastName("Admin")
            .role(UserRole.ROLE_ADMIN)
            .location(UserLocation.builder()
                .address("Teststraße 12")
                .city("Wien")
                .postalCode("1120")
                .country("Austria")
                .build())
            .build();
    }

    void initOrder() {
        order = Order.builder()
            .id(1L)
            .user(user)
            .orderDate(OffsetDateTime.now())
            .build();
    }

    void initEvent() {
        event = Event.builder()
            .seatPrice(12.99f)
            .standingPrice(9.99f)
            .build();
    }

    @Test
    void createInvoicePdf_withValidOrder_createsCorrectEmbeddedFile() {
        assertDoesNotThrow(() -> {
            EmbeddedFile embeddedFile = pdfService.createInvoicePdf(order, List.of(), event);
            assertAll(() -> {
                assertNotNull(embeddedFile);
                assertThat(embeddedFile.getMimeType()).isEqualTo(MediaType.APPLICATION_PDF_VALUE);
                assertThat(embeddedFile.getAllowedViewer()).usingRecursiveComparison().isEqualTo(user);
                // File signature of PDFs is 0x255044462D, see https://en.wikipedia.org/wiki/List_of_file_signatures
                assertThat(embeddedFile.getData()).startsWith(0x25, 0x50, 0x44, 0x46, 0x2D);
            });
        });
    }
}
