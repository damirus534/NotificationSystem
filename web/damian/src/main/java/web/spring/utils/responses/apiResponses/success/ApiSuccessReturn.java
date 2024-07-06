package web.spring.utils.responses.apiResponses.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiSuccessReturn {

    private final String message;

    private final Object value;
}
