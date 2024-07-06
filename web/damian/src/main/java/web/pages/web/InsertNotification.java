package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import web.spring.api.modules.notification.NotificationService;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

public class InsertNotification {

    @Getter
    @Setter
    @PageActivationContext
    private String userJson;

    private User user;
    @Component(id="insertForm")
    private Form insertForm;
    @Property
    private String formErrorMessage = "";

    @Property
    private boolean validationFailed;
    @Property
    private boolean hasPermission;

    @Property
    @Validate("required")
    private String address;
    @Property
    @Validate("required")
    private String login;

    @Property
    @Validate("required")
    private String content;
    @Component(id="address")
    private TextField addressField;
    @Component(id="login")
    private TextField loginField;
    @Component(id="content")
    private TextField contentField;

    @InjectPage
    private Notification notificationPage;

    private NotificationService notificationService = SpringUtils.ctx.getBean(NotificationService.class);

    void onActivate() {
        user = ArgumentsEncryptionUtils.decryptToObject(userJson, User.class);
        hasPermission = user != null;
    }

    void onValidateFromInsertForm() {
        if (login == null){
            handleError("Assigned user field cannot be empty");
        }else if (address == null){
            handleError("Address field cannot be empty");
        }else if (content == null){
            handleError("Content field cannot be empty");
        }
        ServiceResponse<web.spring.api.modules.notification.Notification> addNotificationResponse = notificationService.addNotification(user, login, address, content);
        if (!addNotificationResponse.isSuccess()){
            handleError(addNotificationResponse.getMessage());
        }
    }
    private void handleError(String errorMessage){
        insertForm.recordError("record error to not emit onSuccess");
        validationFailed = true;
        formErrorMessage = errorMessage;
    }

    public boolean hasErrors() {
        return insertForm.getHasErrors();
    }

    Object onSuccessFromInsertForm() {
        validationFailed = false;
        insertForm.clearErrors();
        notificationPage.setUserJson(userJson);
        return notificationPage;
    }
}
