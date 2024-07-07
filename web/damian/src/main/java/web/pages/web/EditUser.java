package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.corelib.components.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

public class EditUser {
    @Getter
    @Setter
    @PageActivationContext
    private String usersJson;

    @Property
    private User user;

    private User adminUser;

    @Component
    private Form editForm;

    @Property
    private boolean hasPermission;

    @Property
    private boolean validationFailed;

    @Property
    private String formErrorMessage = "";

    @Property
    private boolean isAdmin;
    @Property
    private boolean hasMobileAccess;

    @Property
    private boolean modifyNtfAddr;
    @Property
    private boolean modifyNtfContent;
    @Property
    private boolean modifyNtfAssiuser;

    @InjectPage
    private Admin admin;

    private UserService userService = SpringUtils.ctx.getBean(UserService.class);

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    void onActivate() {
        hasPermission = true;
        Pair<User, User> pageEntries = ArgumentsEncryptionUtils
                .decryptToObjectPair(usersJson,User.class, User.class);
        if (pageEntries == null || pageEntries.getFirst() == null || pageEntries.getSecond() == null){
            hasPermission = false;
        }else{
            adminUser = pageEntries.getFirst();
            user = pageEntries.getSecond();
            isAdmin = user.isAdmin();
            hasMobileAccess = user.isMobileAppAccess();
            modifyNtfAddr = user.isModifyNtfAddr();
            modifyNtfContent = user.isModifyNtfContent();
            modifyNtfAssiuser = user.isModifyNtfAssiuser();
        }
    }

    void onValidateFromEditForm(){
        user.setAdmin(isAdmin);
        user.setMobileAppAccess(hasMobileAccess);
        user.setModifyNtfAddr(modifyNtfAddr);
        user.setModifyNtfContent(modifyNtfContent);
        user.setModifyNtfAssiuser(modifyNtfAssiuser);
        ServiceResponse<User> editUserResponse = userService.editUser(user);
        if (!editUserResponse.isSuccess()){
            handleError(editUserResponse.getMessage());
        }
    }

    private void handleError(String errorMessage){
        editForm.recordError("record error to not emit onSuccess");
        validationFailed = true;
        formErrorMessage = errorMessage;
    }

    public boolean hasErrors() {
        return editForm.getHasErrors();
    }

    Object onSuccessFromEditForm() {
        validationFailed = false;
        editForm.clearErrors();
        admin.setUserJson(ArgumentsEncryptionUtils.encryptToJson(adminUser));
        return admin;
    }


}