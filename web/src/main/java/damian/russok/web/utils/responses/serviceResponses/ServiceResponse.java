package damian.russok.web.utils.responses.serviceResponses;

import damian.russok.web.utils.responses.apiResponses.exceptions.ApiExceptionHandler;
import damian.russok.web.utils.responses.apiResponses.exceptions.ApiRequestException;
import damian.russok.web.utils.responses.apiResponses.success.ApiSuccessResponse;
import damian.russok.web.utils.responses.apiResponses.success.ApiSuccessResponseHandler;
import damian.russok.web.utils.responses.apiResponses.success.ApiSuccessReturn;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ServiceResponse<T> {

    public enum ServiceResponseStatus{
        SUCCESS, ACCEPTED, CREATED, VALIDATION_ERROR, DUPLICATE, NO_PERMISSION, UNKNOWN_ERROR
    }

    private final ServiceResponseStatus responseStatus;
    private final String message;
    private final T value;

    public ServiceResponse(ServiceResponseStatus responseStatus, String message, T value) {
        this.responseStatus = responseStatus;
        this.message = message;
        this.value = value;
    }

    public boolean isSuccess() {
        return responseStatus == ServiceResponseStatus.SUCCESS ||
               responseStatus == ServiceResponseStatus.ACCEPTED ||
               responseStatus == ServiceResponseStatus.CREATED;
    }

    public ResponseEntity<Object> toApiReturn() {
        switch (this.responseStatus) {
            case SUCCESS : return new ApiSuccessResponseHandler().handleOkResponse(new ApiSuccessReturn(message, value));
            case ACCEPTED : return new ApiSuccessResponseHandler().handleAcceptedResponse(new ApiSuccessReturn(message, value));
            case CREATED : return new ApiSuccessResponseHandler().handleCreatedResponse(new ApiSuccessReturn(message, value));
            case DUPLICATE : return new ApiExceptionHandler().handleApiRequestException(new ApiRequestException(message));
            case VALIDATION_ERROR : return new ApiExceptionHandler().handleValidationErrorException(new ApiRequestException(message));
            case NO_PERMISSION : return new ApiExceptionHandler().handleNoPermissionException(new ApiRequestException(message));
            default: return new ApiExceptionHandler().handleApiRequestException(new ApiRequestException(message));
        }
    }
}