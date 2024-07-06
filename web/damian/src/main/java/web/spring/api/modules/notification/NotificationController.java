package web.spring.api.modules.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.spring.utils.responses.apiResponses.exceptions.ApiFailureResponseHandler;

@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final ApiFailureResponseHandler apiExceptionHandler = new ApiFailureResponseHandler();

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
