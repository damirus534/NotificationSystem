package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

import java.util.List;

public class InsertUser {
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
    @Validate("required")
    private String login;

    @Property
    @Validate("required")
    private String password;
    @Property
    @Validate("required")
    private String rpassword;
    @Component(id="login")
    private TextField loginField;
    @Component(id="password")
    private PasswordField passwordField;
    @Component(id="rpassword")
    private PasswordField rpasswordField;

    @InjectPage
    private Admin adminPage;

    @Property
    private boolean validationFailed;
    @Property
    private boolean hasPermission;

    private UserService userService = SpringUtils.ctx.getBean(UserService.class);

    public boolean hasErrors() {
        return insertForm.getHasErrors();
    }

    void onActivate() {
        user = ArgumentsEncryptionUtils.decryptToObject(userJson, User.class);
        hasPermission = user != null;
    }

    private void handleError(String errorMessage){
        insertForm.recordError("record error to not emit onSuccess");
        validationFailed = true;
        formErrorMessage = errorMessage;
    }
    void onValidateFromInsertForm() {
        if (login == null){
            handleError("Login field cannot be empty");
        }else if (password == null){
            handleError("Password field cannot be empty");
        }else if (rpassword == null){
            handleError("Repeat password field cannot be empty");
        }else if (!password.equals(rpassword)){
            handleError("Password fields are not equal");
        } else{
            ServiceResponse<User> registerUserResponse = userService.registerUser(login, password);
            if (!registerUserResponse.isSuccess()){
                handleError(registerUserResponse.getMessage());
            }
        }
    }

    Object onSuccessFromInsertForm() {
        validationFailed = false;
        insertForm.clearErrors();
        adminPage.setUserJson(userJson);
        return adminPage;
    }
}
