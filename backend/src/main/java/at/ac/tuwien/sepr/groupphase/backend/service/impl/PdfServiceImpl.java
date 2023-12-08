package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.config.FreemarkerConfig;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
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
    public EmbeddedFile createInvoicePdf(@NotNull Order order) throws IOException, TemplateException {
        Template template = freemarkerConfiguration.getTemplate(freemarkerConfig.getTemplates().get("invoice"));
        try (Writer stringWriter = new StringWriter();
             ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("order", order);
            template.process(variables, stringWriter);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withW3cDocument(new W3CDom().fromJsoup(Jsoup.parse(stringWriter.toString(), "UTF-8")), "/");
            builder.toStream(byteOutputStream);
            builder.run();

            EmbeddedFile embeddedFile = new EmbeddedFile();
            embeddedFile.setData(byteOutputStream.toByteArray());
            embeddedFile.setAllowedViewer(order.getUser());
            embeddedFile.setMimeType(MediaType.APPLICATION_PDF_VALUE);
            return embeddedFile;
        }
    }

    @Override
    public EmbeddedFile createCancellationInvoicePdf(Order order) {
        // TODO: Create
        return null;
    }

    @Override
    public EmbeddedFile createTicketPdf(Ticket ticket) {
        // TODO: Create
        return null;
    }
}
