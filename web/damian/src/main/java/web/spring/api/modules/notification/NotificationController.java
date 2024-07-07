package web.spring.api.modules.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.spring.api.modules.user.User;
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

    @GetMapping("/assigned/{login}")
    public ResponseEntity<Object> getNotificationsAssignedForUser(@PathVariable String login) {
        return notificationService.findNotificationsAssignedToUser(login).toApiReturn();
    }

    @PostMapping("/edit")
    public ResponseEntity<Object> editNotification(@RequestBody Notification notification){
        return notificationService.editNotification(notification, notification.getAssignedLogin().getLogin(), false).toApiReturn();
    }
}
