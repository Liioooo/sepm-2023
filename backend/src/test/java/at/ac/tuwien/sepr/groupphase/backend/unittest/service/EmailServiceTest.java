package at.ac.tuwien.sepr.groupphase.backend.unittest.service;

import at.ac.tuwien.sepr.groupphase.backend.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @SpyBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setup() {
        Mockito.doNothing().when(javaMailSender)
            .send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    void createInvoicePdf_withValidOrder_createsCorrectEmbeddedFile() {
        var recipient = "recipient@mail.com";
        var subject = "This is a test";
        var text = "This is a test email";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Ticketline <noreply@ticketline.com>");
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);

        assertDoesNotThrow(() -> {
            emailService.sendSimpleMail(recipient, subject, text);
        });

        Mockito.verify(javaMailSender).send(message);
    }

}
