package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;

public interface PdfService {

    /**
     * Create an EmbeddedFile for the Invoice PDF.
     *
     * @param order The order of which to create the invoice
     * @return The EmbeddedFile as PDF
     */
    EmbeddedFile createInvoicePdf(@NotNull Order order, @NotNull List<Ticket> tickets, @NotNull Event event) throws IOException, TemplateException;

    /**
     * Create an EmbeddedFile for the Cancellation Invoice PDF.
     *
     * @param order The cancellation order of which to create the invoice
     * @return The EmbeddedFile as PDF
     */
    EmbeddedFile createCancellationInvoicePdf(@NotNull Order order, @NotNull List<Ticket> tickets, @NotNull Event event) throws IOException, TemplateException;

    /**
     * Create a PDF for a Ticket.
     *
     * @param ticket The ticket for which to create the PDF
     * @return The EmbeddedFile as PDF
     */
    EmbeddedFile createTicketPdf(@NotNull Order order, @NotNull Ticket ticket, @NotNull Event event) throws IOException, TemplateException ;
}
