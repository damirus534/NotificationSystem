package damian.russok.web.utils.responses.apiResponses.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ApiSuccessResponse {

    private final String message;

    private final Object value;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;


    public ApiSuccessResponse(String message, Object value, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.value = value;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
