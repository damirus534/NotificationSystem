package web.pages.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.http.Link;
import web.spring.api.modules.notification.NotificationService;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Notification {

    @Getter
    @Setter
    @PageActivationContext
    private String userJson;

    @Property
    private web.spring.api.modules.notification.Notification notification;

    @Property
    private boolean hasPermission;

    @Property
    private String formErrorMessage = "";

    @Component(id="notifform")
    private Form notifform;

    @Property
    private List<web.spring.api.modules.notification.Notification> notificationsToDisplay;

    private User user;

    @InjectPage
    private EditNotification editNotificationPage;

    @InjectPage
    private InsertNotification insertNotificationPage;

    private NotificationService notificationService = SpringUtils.ctx.getBean(NotificationService.class);

    public boolean isEmptyList() {
        return notificationsToDisplay == null || notificationsToDisplay.isEmpty();
    }

    public boolean hasErrors() {
        return notifform.getHasErrors();
    }

    void onActivate() {
        user = ArgumentsEncryptionUtils.decryptToObject(userJson, User.class);
        hasPermission = user != null;
        ServiceResponse<List< web.spring.api.modules.notification.Notification>> getCreatedNotificationsResponse= notificationService.findAllNotificationsCreatedByUser(user);
        if(getCreatedNotificationsResponse.isSuccess()){
            notificationsToDisplay = getCreatedNotificationsResponse.getValue();
        }else {
            formErrorMessage = getCreatedNotificationsResponse.getMessage();
        }
    }

    @OnEvent(component = "createNotificationLink")
    Object onCreateNotification() {
        insertNotificationPage.setUserJson(userJson);
        return insertNotificationPage;
    }

    Object onActionFromEditLink(Long notificationId) {
        web.spring.api.modules.notification.Notification notification = findNotificationInList(notificationId);
        notification.getAssignedLogin().setUserNotifications(new ArrayList<>());
        notification.getNotifiedLogin().setUserNotifications(new ArrayList<>());
        user.setUserNotifications(new ArrayList<>());
        user.setNotificationsAssigned(new ArrayList<>());
        String usersJson = ArgumentsEncryptionUtils.encryptToJson(user, notification);
        editNotificationPage.setEntryJson(usersJson);
        return editNotificationPage;
    }

    private void handleError(String errorMessage){
        notifform.recordError("record error to not emit onSuccess");
        formErrorMessage = errorMessage;
    }

    private web.spring.api.modules.notification.Notification findNotificationInList(long ntfId){
        return notificationsToDisplay.stream()
                .filter(ntf -> ntf.getNotificationId()==ntfId)
                .findFirst()
                .get();
    }


}
