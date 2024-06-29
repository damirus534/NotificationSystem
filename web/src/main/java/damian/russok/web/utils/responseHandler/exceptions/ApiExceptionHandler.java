package damian.russok.web.utils.responseHandler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

public class ApiExceptionHandler {

    private final HttpStatus badRequestHttpStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus validationErrorHttpStatus = HttpStatus.NOT_ACCEPTABLE;
    private final HttpStatus noPermissionHttpStatus = HttpStatus.FORBIDDEN;

    private ZonedDateTime actualTime() { return ZonedDateTime.now(); }
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ApiException apiException= new ApiException(e.getMessage(), badRequestHttpStatus, actualTime());
        return new ResponseEntity<>(apiException, badRequestHttpStatus);
    }

    public ResponseEntity<Object> handleValidationErrorException(ApiRequestException e){
        ApiException apiException = new ApiException(e.getMessage(), validationErrorHttpStatus, actualTime());
        return new ResponseEntity<>(apiException, validationErrorHttpStatus);
    }

    public ResponseEntity<Object> handleNoPermissionException(ApiRequestException e){
        ApiException apiException = new ApiException(e.getMessage(), noPermissionHttpStatus, actualTime());
        return new ResponseEntity<>(apiException, noPermissionHttpStatus);
    }

}
