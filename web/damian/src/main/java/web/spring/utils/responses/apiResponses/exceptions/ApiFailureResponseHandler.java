package web.spring.utils.responses.apiResponses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

public class ApiFailureResponseHandler {

    private final HttpStatus badRequestHttpStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus validationErrorHttpStatus = HttpStatus.NOT_ACCEPTABLE;
    private final HttpStatus noPermissionHttpStatus = HttpStatus.FORBIDDEN;
    private final HttpStatus notFoundHttpStatus = HttpStatus.NOT_FOUND;

    private ZonedDateTime actualTime() { return ZonedDateTime.now(); }
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiFailureResponse apiFailureResponse = new ApiFailureResponse(e.getMessage(), badRequestHttpStatus, actualTime());
        return new ResponseEntity<>(apiFailureResponse, badRequestHttpStatus);
    }

    public ResponseEntity<Object> handleValidationErrorException(ApiRequestException e){
        ApiFailureResponse apiFailureResponse = new ApiFailureResponse(e.getMessage(), validationErrorHttpStatus, actualTime());
        return new ResponseEntity<>(apiFailureResponse, validationErrorHttpStatus);
    }

    public ResponseEntity<Object> handleNoPermissionException(ApiRequestException e){
        ApiFailureResponse apiFailureResponse = new ApiFailureResponse(e.getMessage(), noPermissionHttpStatus, actualTime());
        return new ResponseEntity<>(apiFailureResponse, noPermissionHttpStatus);
    }

    public ResponseEntity<Object> handleNotFoundException(ApiRequestException e){
        ApiFailureResponse apiFailureResponse = new ApiFailureResponse(e.getMessage(), notFoundHttpStatus, actualTime());
        return new ResponseEntity<>(apiFailureResponse, notFoundHttpStatus);
    }

}
