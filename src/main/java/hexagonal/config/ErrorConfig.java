package hexagonal.config;

import hexagonal.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorConfig {

    @ExceptionHandler
    public ProblemDetail handle(BusinessException exception) {
        var problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetails.setDetail(exception.getMessage());
        return problemDetails;
    }

    @ExceptionHandler
    public ProblemDetail handle(UsernameNotFoundException exception) {
        var problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetails.setDetail(exception.getMessage());
        return problemDetails;
    }

    @ExceptionHandler
    public ProblemDetail handle(MethodArgumentNotValidException exception) {
        var problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            problemDetails.setProperty(fieldName, errorMessage);
        });
        return problemDetails;
    }
}
