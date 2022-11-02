package live.bolaocopadomundo.api.resources.exceptions;

import live.bolaocopadomundo.api.services.exceptions.DatabaseException;
import live.bolaocopadomundo.api.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(createStandardError(status, "Resource not found", e, request));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(createStandardError(status, "Database exception", e, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(createValidationError(status, e, request));
    }

    private StandardError createStandardError(HttpStatus status, String message, Exception e, HttpServletRequest request) {
        return new StandardError(Instant.now(), status.value(), message, e.getMessage(), request.getRequestURI());
    }

    private ValidationError createValidationError(HttpStatus status, Exception e, HttpServletRequest request) {
        ValidationError err = new ValidationError(
                Instant.now(),
                status.value(),
                "Validation exception",
                e.getMessage(),
                request.getRequestURI());

        for (FieldError field : ((BindException) e).getBindingResult().getFieldErrors()) {
            err.addError(field.getField(), field.getDefaultMessage());
        }

        return err;
    }
}
