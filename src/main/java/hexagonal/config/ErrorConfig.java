package hexagonal.config;

import hexagonal.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
}
