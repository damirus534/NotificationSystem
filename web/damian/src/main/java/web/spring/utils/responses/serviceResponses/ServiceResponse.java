package web.spring.utils.responses.serviceResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import web.spring.utils.responses.apiResponses.exceptions.ApiFailureResponseHandler;
import web.spring.utils.responses.apiResponses.exceptions.ApiRequestException;
import web.spring.utils.responses.apiResponses.success.ApiSuccessResponseHandler;
import web.spring.utils.responses.apiResponses.success.ApiSuccessReturn;

@Getter
@AllArgsConstructor
public class ServiceResponse<T> {

    public enum ServiceResponseStatus{
        SUCCESS, ACCEPTED, CREATED, VALIDATION_ERROR, DUPLICATE, NO_PERMISSION, UNKNOWN_ERROR, NOT_FOUND
    }

    private final ServiceResponseStatus responseStatus;
    private final String message;
    private final T value;

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
            case DUPLICATE : return new ApiFailureResponseHandler().handleApiRequestException(new ApiRequestException(message));
            case VALIDATION_ERROR : return new ApiFailureResponseHandler().handleValidationErrorException(new ApiRequestException(message));
            case NO_PERMISSION : return new ApiFailureResponseHandler().handleNoPermissionException(new ApiRequestException(message));
            case NOT_FOUND : return new ApiFailureResponseHandler().handleNotFoundException(new ApiRequestException(message));
            default: return new ApiFailureResponseHandler().handleApiRequestException(new ApiRequestException(message));
        }
    }
}