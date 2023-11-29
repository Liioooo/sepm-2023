package at.ac.tuwien.sepr.groupphase.backend.exception;

public class PublicFileStorageException extends RuntimeException {

    public PublicFileStorageException() {
    }

    public PublicFileStorageException(String message) {
        super(message);
    }

    public PublicFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
