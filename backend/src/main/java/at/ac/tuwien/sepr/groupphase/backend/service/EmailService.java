package at.ac.tuwien.sepr.groupphase.backend.service;

public interface EmailService {

    /**
     * Sends a simple email.
     *
     * @param recipient the recipient of the email
     * @param subject   the subject of the email
     * @param text      the text of the email
     */
    void sendSimpleMail(String recipient, String subject, String text);
}
