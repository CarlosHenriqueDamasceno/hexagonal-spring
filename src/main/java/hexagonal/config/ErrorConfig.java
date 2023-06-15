package hexagonal.config;

import hexagonal.shared.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorConfig {

    @ExceptionHandler
    public ProblemDetail handle(BusinessException exception) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        pd.setDetail(exception.getMessage());
        return pd;
    }
}
