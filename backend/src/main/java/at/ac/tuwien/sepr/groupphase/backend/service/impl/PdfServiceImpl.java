package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.config.FreemarkerConfig;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory;
import at.ac.tuwien.sepr.groupphase.backend.qrcode.QRCodeGenerator;
import at.ac.tuwien.sepr.groupphase.backend.service.PdfService;
import com.google.zxing.WriterException;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    private final Configuration freemarkerConfiguration;
    private final FreemarkerConfig.Config freemarkerConfig;

    public PdfServiceImpl(Configuration freemarkerConfiguration, FreemarkerConfig.Config freemarkerConfig) {
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public EmbeddedFile createInvoicePdf(@NotNull Order order, @NotNull List<Ticket> tickets, @NotNull Event event) throws IOException, TemplateException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("order", order);
        variables.put("event", event);
        variables.put("tickets", tickets);
        variables.put("standingTicketsCount", tickets.stream().filter((ticket) -> ticket.getTicketCategory() == TicketCategory.STANDING).count());
        variables.put("orderDate", order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        variables.put("totalPrice", tickets.stream().map((ticket) -> {
            if (ticket.getTicketCategory() == TicketCategory.STANDING) {
                return event.getStandingPrice();
            } else if (ticket.getTicketCategory() == TicketCategory.SEATING) {
                return event.getSeatPrice();
            }
            return 0;
        }).mapToDouble(Number::doubleValue).sum());
        EmbeddedFile pdf = this.generatePdf("invoice", variables);
        pdf.setAllowedViewer(order.getUser());
        return pdf;
    }

    @Override
    public EmbeddedFile createCancellationInvoicePdf(@NotNull Order order, @NotNull List<Ticket> tickets, @NotNull Event event) throws IOException, TemplateException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("order", order);
        variables.put("invoiceNumber", order.getId() + "/" + (order.getCancellationReceipts() == null ? 1 : order.getCancellationReceipts().size() + 1));
        variables.put("event", event);
        variables.put("tickets", tickets);
        variables.put("standingTicketsCount", tickets.stream().filter((ticket) -> ticket.getTicketCategory() == TicketCategory.STANDING).count());
        variables.put("orderDate", OffsetDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        variables.put("totalPrice", -tickets.stream().map((ticket) -> {
            if (ticket.getTicketCategory() == TicketCategory.STANDING) {
                return event.getStandingPrice();
            } else if (ticket.getTicketCategory() == TicketCategory.SEATING) {
                return event.getSeatPrice();
            }
            return 0;
        }).mapToDouble(Number::doubleValue).sum());
        EmbeddedFile pdf = this.generatePdf("cancellation_invoice", variables);
        pdf.setAllowedViewer(order.getUser());
        return pdf;
    }

    @Override
    public EmbeddedFile createTicketPdf(@NotNull Order order, @NotNull Ticket ticket, @NotNull Event event) throws IOException, TemplateException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("order", order);
        variables.put("event", event);
        variables.put("eventDate", event.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        variables.put("name", order.getUser());
        variables.put("ticket", ticket);
        if (event.getArtist().getFictionalName() != null) {
            variables.put("artist", event.getArtist().getFictionalName());
        } else if (event.getArtist().getFirstname() != null) {
            variables.put("artist", event.getArtist().getFirstname() + " " + event.getArtist().getLastname());
        }
        variables.put("ticketNr", ticket.getId());
        variables.put("orderDate", OffsetDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        if (ticket.getTicketCategory() == TicketCategory.STANDING) {
            variables.put("category", TicketCategory.STANDING);
            variables.put("price", event.getStandingPrice());
        } else if (ticket.getTicketCategory() == TicketCategory.SEATING) {
            variables.put("category", TicketCategory.SEATING);
            variables.put("price", event.getSeatPrice());
        }
        /* TODO: add QR-Code
        try {
            // generate and save QR Code image in resources/qr_codes folder
            qrCodeGenerator.generateQRCodeImage(ticket.getId().toString(), 250, 250, "./src/main/resources/qr_codes/" + ticket.getId().toString() + ".png");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
         */

        EmbeddedFile pdf = this.generatePdf("ticket", variables);
        pdf.setAllowedViewer(order.getUser());
        return pdf;
    }

    private <K, V> EmbeddedFile generatePdf(String templateName, Map<K, V> variables) throws IOException, TemplateException {
        Template template = freemarkerConfiguration.getTemplate(freemarkerConfig.getTemplates().get(templateName));
        try (Writer stringWriter = new StringWriter();
             ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
            template.process(variables, stringWriter);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFont(new ClassPathResource("pdf/templates/fonts/NotoSerif-Regular.ttf").getFile(), "NotoSerif", 400, BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ClassPathResource("pdf/templates/fonts/NotoSerif-Italic.ttf").getFile(), "NotoSerif", 400, BaseRendererBuilder.FontStyle.ITALIC, true);
            builder.useFont(new ClassPathResource("pdf/templates/fonts/NotoSerif-Bold.ttf").getFile(), "NotoSerif", 700, BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ClassPathResource("pdf/templates/fonts/NotoSerif-BoldItalic.ttf").getFile(), "NotoSerif", 700, BaseRendererBuilder.FontStyle.ITALIC, true);

            builder.withW3cDocument(
                new W3CDom().fromJsoup(Jsoup.parse(stringWriter.toString(), "UTF-8")),
                freemarkerConfig.getTemplates().containsKey("path")
                    ? new ClassPathResource(freemarkerConfig.getTemplates().get("path")).getFile().toURI().toURL().toString()
                    : "/"
            );
            builder.toStream(byteOutputStream);
            builder.run();

            EmbeddedFile embeddedFile = new EmbeddedFile();
            embeddedFile.setData(byteOutputStream.toByteArray());
            embeddedFile.setMimeType(MediaType.APPLICATION_PDF_VALUE);
            return embeddedFile;
        }
    }
}
