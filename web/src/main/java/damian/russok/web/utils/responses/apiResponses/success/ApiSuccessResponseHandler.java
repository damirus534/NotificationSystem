package damian.russok.web.utils.responses.apiResponses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

public class ApiSuccessResponseHandler {
    private final HttpStatus acceptedHttpStatus = HttpStatus.ACCEPTED;
    private final HttpStatus createdHttpStatus = HttpStatus.CREATED;
    private final HttpStatus okHttpStatus = HttpStatus.OK;


    private ZonedDateTime actualTime() { return ZonedDateTime.now(); }
    public ResponseEntity<Object> handleAcceptedResponse(ApiSuccessReturn response){
        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse(
                response.getMessage(),
                response.getValue(),
                acceptedHttpStatus,
                actualTime()
        );
        return new ResponseEntity<>(apiSuccessResponse, acceptedHttpStatus);
    }

    public ResponseEntity<Object> handleCreatedResponse(ApiSuccessReturn response){
        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse(
                response.getMessage(),
                response.getValue(),
                createdHttpStatus,
                actualTime()
        );
        return new ResponseEntity<>(apiSuccessResponse, createdHttpStatus);
    }


    public ResponseEntity<Object> handleOkResponse(ApiSuccessReturn response){
        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse(
                response.getMessage(),
                response.getValue(),
                okHttpStatus,
                actualTime()
        );
        return new ResponseEntity<>(apiSuccessResponse, okHttpStatus);
    }
}
