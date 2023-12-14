package at.ac.tuwien.sepr.groupphase.backend.exception;


public class ConflictException extends RuntimeException {

    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Exception e) {
        super(e);
    }
}
