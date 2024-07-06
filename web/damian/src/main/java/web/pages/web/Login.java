package web.pages.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

public class Login {

    @Property
    @Validate("required")
    private String username;

    @Property
    @Validate("required")
    private String password;

    @Component(id="lform")
    private Form lform;

    @Component(id="username")
    private TextField usernameField;
    @Component(id="password")
    private PasswordField passwordField;

    @Inject
    private AlertManager alertManager;

    @Property
    private boolean validationFailed;

    @Property
    private String formErrorMessage = "";

    @InjectPage()
    private Admin adminPage;

    @InjectPage()
    private Notification notificationPage;



    private UserService userService = SpringUtils.ctx.getBean(UserService.class);

    private static final Logger logger = LoggerFactory.getLogger(Login.class);


    void onValidateFromLform() {
        boolean validationSuccess = false;
        if (username == null){
            handleError("Login field cannot be empty");
        }else if (password == null){
            handleError("Password field cannot be empty");
        }else{
            ServiceResponse<User> checkCredentialsResultResult = userService.checkCredentials(username, password);
            if (!checkCredentialsResultResult.isSuccess()){
                handleError(checkCredentialsResultResult.getMessage());
            } else {
                String userJson = ArgumentsEncryptionUtils.encryptToJson(checkCredentialsResultResult.getValue());
                if (userJson==null){
                    handleError("Error occurred while parsing user object");
                }
                if (checkCredentialsResultResult.getValue().isAdmin()){
                    adminPage.setUserJson(userJson);
                }else {
                    notificationPage.setUserJson(userJson);
                }
            }
        }
    }

    private void handleError(String errorMessage){
        lform.recordError("record error to not emit onSuccess");
        validationFailed = true;
        formErrorMessage = errorMessage;
    }

    Object onSuccessFromLform() {
        validationFailed = false;
        lform.clearErrors();
        if (adminPage==null || adminPage.getUserJson()!=null) {
            return adminPage;
        }else {
            return notificationPage;
        }
    }

    public boolean hasErrors() {
        return lform.getHasErrors();
    }
}
