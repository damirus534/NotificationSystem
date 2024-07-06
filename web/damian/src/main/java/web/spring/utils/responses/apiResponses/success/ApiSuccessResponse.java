package web.spring.utils.responses.apiResponses.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApiSuccessResponse {

    private final String message;
    private final Object value;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
