package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.corelib.components.TextField;
import org.springframework.data.util.Pair;
import web.spring.api.modules.notification.Notification;
import web.spring.api.modules.notification.NotificationService;
import web.spring.api.modules.user.User;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

public class EditNotification {

    @Getter
    @Setter
    @PageActivationContext
    private String entryJson; //user,notification

    @Property
    private User user;
    private Notification notification;

    @Property
    private boolean hasPermission;

    @Component(id="editForm")
    private Form editForm;

    @Component(id="deleteForm")
    private Form deleteForm;

    @Property
    private String formErrorMessage = "";

    @Property
    private String deleteFormErrorMessage = "";

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
    private web.pages.web.Notification notificationPage;

    private NotificationService notificationService = SpringUtils.ctx.getBean(NotificationService.class);

    void onActivate() {
        hasPermission = true;
        Pair<User, Notification> pageEntries = ArgumentsEncryptionUtils
                .decryptToObjectPair(entryJson,User.class, Notification.class);
        if (pageEntries == null || pageEntries.getFirst() == null || pageEntries.getSecond() == null){
            hasPermission = false;
        }else{
            notification = pageEntries.getSecond();
            user = pageEntries.getFirst();
            address = notification.getAddress();
            login = notification.getAssignedLogin().getLogin();
            content = notification.getContent();
        }
    }

    private void handleError(String errorMessage){
        editForm.recordError("record error to not emit onSuccess");
        formErrorMessage = errorMessage;
    }

    private void handleDeleteError(String errorMessage){
        deleteForm.recordError("record error to not emit onSuccess");
        deleteFormErrorMessage = errorMessage;
    }

    public boolean hasErrors() {
        return editForm.getHasErrors();
    }

    public boolean hasDeleteErrors() {
        return deleteForm.getHasErrors();
    }

    void onValidateFromEditForm(){
        if (address == null || address.isEmpty()) {
            handleError("Address field cannot be empty");
        }else if (login == null || login.isEmpty()) {
            handleError("You have to assign user");
        }else if (content == null || content.isEmpty()) {
            handleError("Content cannot be empty");
        }
        notification.setAddress(address);
        notification.setContent(content);
        ServiceResponse<Notification> editNotificationServiceResponse = notificationService.editNotification(notification, login);
        if(!editNotificationServiceResponse.isSuccess()){
            handleError(editNotificationServiceResponse.getMessage());
        }
    }

    void onValidateFromDeleteForm(){
        ServiceResponse<Object> deleteResponse = notificationService.deleteNotification(notification);
        if(!deleteResponse.isSuccess()){
            handleDeleteError(deleteResponse.getMessage());
        }
    }

    Object onSuccessFromEditForm() {
        editForm.clearErrors();
        return navigateToNotificationPage();
    }

    Object onSuccessFromDeleteForm(){
        deleteForm.clearErrors();
        return navigateToNotificationPage();
    }

    private web.pages.web.Notification navigateToNotificationPage(){
        notificationPage.setUserJson(ArgumentsEncryptionUtils.encryptToJson(user));
        return notificationPage;
    }



}
