package damian.russok.web.utils.responses.apiResponses.success;

import lombok.Getter;

@Getter
public class ApiSuccessReturn {

    private final String message;

    private final Object value;

    public ApiSuccessReturn(String message, Object value) {
        this.message = message;
        this.value = value;
    }
}
