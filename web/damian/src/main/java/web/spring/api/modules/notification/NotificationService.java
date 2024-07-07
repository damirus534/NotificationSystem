package web.spring.api.modules.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserRepository;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.validators.AddressValidator;

import java.sql.Timestamp;
import java.util.List;

import static web.spring.utils.responses.serviceResponses.ServiceResponse.ServiceResponseStatus.*;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository){
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public ServiceResponse<Object> deleteNotification(Notification notification) {
        try{
            notificationRepository.delete(notification);
        }catch (Exception ex){
            return new ServiceResponse<>(UNKNOWN_ERROR, "Unexpected error occurred: "+ex.getLocalizedMessage(), null);
        }
        return new ServiceResponse<>(SUCCESS, "Notification deleted", null);
    }

    public ServiceResponse<List<Notification>> findAllNotificationsCreatedByUser(User notifiedUser) {
        List<Notification> foundNotifications = notificationRepository.findAllByNotifiedLogin(notifiedUser);
        if (foundNotifications == null || foundNotifications.isEmpty()) {
            return new ServiceResponse<>(NOT_FOUND, "No notifications found for the user", null);
        }
        return new ServiceResponse<>(SUCCESS, "Found " + foundNotifications.size() + " notifications", foundNotifications);
    }

    public ServiceResponse<Notification> addNotification(User currentUser, String assignedUserLogin, String address, String content) {
        if (currentUser.getLogin().equals(assignedUserLogin)) {
            return new ServiceResponse<>(FORBIDDEN_ACTION, "You cannot assign a notification to yourself", null);
        }

        User assignedUser = userRepository.findByLogin(assignedUserLogin);
        if (assignedUser == null) {
            return new ServiceResponse<>(FORBIDDEN_ACTION, "User that you try to assign does not exist", null);
        }

        if (!AddressValidator.isPolishAddressValid(address)) {
            return new ServiceResponse<>(VALIDATION_ERROR, "Invalid address format. Postal code in 00-000 format required.", null);
        }
        Notification notification = new Notification();
        notification.setNotifiedLogin(currentUser);
        notification.setAssignedLogin(assignedUser);
        notification.setDate(new Timestamp(System.currentTimeMillis()));
        notification.setAddress(address);
        notification.setContent(content);
        try{
            notificationRepository.save(notification);
        }catch (Exception ex){
            return new ServiceResponse<>(UNKNOWN_ERROR, "Unexpected error occurred: "+ex.getLocalizedMessage(), null);
        }
        return new ServiceResponse<>(SUCCESS, "Notification added successfully", notification);
    }

    public ServiceResponse<Notification> editNotification(Notification notification, String newAssignedLogin) {
        if (notification.getNotifiedLogin().getLogin().equals(newAssignedLogin)) {
            return new ServiceResponse<>(FORBIDDEN_ACTION, "You cannot assign a notification to yourself", null);
        }

        User assignedUser = userRepository.findByLogin(newAssignedLogin);
        if (assignedUser == null) {
            return new ServiceResponse<>(FORBIDDEN_ACTION, "User that you try to assign does not exist", null);
        }

        if (!AddressValidator.isPolishAddressValid(notification.getAddress())) {
            return new ServiceResponse<>(VALIDATION_ERROR, "Invalid address format. Expected format: any text 00-000 any text.", null);
        }
        notification.setAssignedLogin(assignedUser);
        notificationRepository.saveAndFlush(notification);

        return new ServiceResponse<>(SUCCESS, "Notification updated successfully", notification);
    }


}
