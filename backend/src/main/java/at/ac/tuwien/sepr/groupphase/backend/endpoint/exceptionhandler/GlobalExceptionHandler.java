package at.ac.tuwien.sepr.groupphase.backend.endpoint.exceptionhandler;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ApiErrorDto;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.exception.ForbiddenException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InternalServerException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.PublicFileStorageException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.invoke.MethodHandles;

/**
 * Register all your Java exceptions here to map them into meaningful HTTP exceptions
 * If you have special cases which are only important for specific endpoints, use ResponseStatusExceptions
 * https://www.baeldung.com/exception-handling-for-rest-with-spring#responsestatusexception
 * Error handling Tutorial
 * https://www.toptal.com/java/spring-boot-rest-api-error-handling
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        var error = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ConflictException.class})
    protected ResponseEntity<Object> handleConflict(ConflictException ex) {
        var error = new ApiErrorDto(HttpStatus.CONFLICT, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        var error = new ApiErrorDto(HttpStatus.FORBIDDEN, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, error.getHttpStatusCode());
    }

    @ExceptionHandler(value = {PublicFileStorageException.class})
    protected ResponseEntity<Object> handlePublicFileStorageException(PublicFileStorageException ex) {
        var error = new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "File Storage Error: " + ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, error.getHttpStatusCode());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        var error = new ApiErrorDto(HttpStatus.UNAUTHORIZED, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {InternalServerException.class})
    protected ResponseEntity<Object> handleInternalServerException(InternalServerException ex) {
        var error = new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        var error = new ApiErrorDto(HttpStatus.PAYLOAD_TOO_LARGE, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        var error = new ApiErrorDto(status, ex.getBody().getDetail());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addSubError(fieldError.getField(), fieldError.getDefaultMessage());
        });

        logError(error, ex);
        return new ResponseEntity<>(error, headers, status);

    }

    @ExceptionHandler(value = {ForbiddenException.class})
    protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        var error = new ApiErrorDto(HttpStatus.FORBIDDEN, ex.getMessage());

        logError(error, ex);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    private void logError(ApiErrorDto apiErrorDto, Exception exception) {
        LOGGER.warn("Terminating request processing with status {} due to: {}", apiErrorDto.getHttpStatusCode().value(), apiErrorDto.getError());
        LOGGER.error(exception.getMessage(), exception);
    }
}
