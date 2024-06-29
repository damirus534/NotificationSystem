package damian.russok.web.utils.responseHandler.success;

import lombok.Getter;

@Getter
public class ApiSuccessReturn {

    private final String message;

    private final Object value;
    private final Object[] valueArr;

    public ApiSuccessReturn(String message, Object value, Object[] valueArr) {
        this.message = message;
        this.value = value;
        this.valueArr = valueArr;
    }
}
