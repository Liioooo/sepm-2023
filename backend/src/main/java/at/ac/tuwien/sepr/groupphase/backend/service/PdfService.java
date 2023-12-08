package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;

public interface PdfService {
    EmbeddedFile createInvoicePdf(@NotNull Order order) throws IOException, TemplateException;

    EmbeddedFile createCancellationInvoicePdf(Order order);

    EmbeddedFile createTicketPdf(Ticket ticket);
}
