package web.pages.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.spring.api.modules.user.User;
import web.spring.api.modules.user.UserService;
import web.spring.utils.SpringUtils;
import web.spring.utils.responses.serviceResponses.ServiceResponse;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

import java.util.List;

public class Admin {
    @Getter
    @Setter
    @PageActivationContext
    private String userJson;

    @Property
    private boolean hasPermission;

    @Property
    private List<User> usersToDisplay;

    @Property
    private User user;
    @InjectPage
    private EditUser editUser;
    @InjectPage
    private InsertUser insertUserPage;
    @Component(id="permform")
    private Form permform;
    @Property
    private String permissionFormErrorMessage;

    private UserService userService = SpringUtils.ctx.getBean(UserService.class);

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    void onActivate() {
        try {
            user = ArgumentsEncryptionUtils.decryptToObject(userJson, User.class);
            hasPermission = true;
            ServiceResponse<List<User>> findUsersResponse = userService.findAllUsers(user.getLogin());
            if(findUsersResponse.isSuccess()){
                usersToDisplay = findUsersResponse.getValue();
            }else{
                permissionFormErrorMessage = findUsersResponse.getMessage();
            }
        } catch (Exception e) {
            hasPermission = false;
        }
    }

    public boolean isEmptyList() {
        return usersToDisplay == null || usersToDisplay.isEmpty();
    }

    Object onActionFromEditLink(String login) {
        String usersJson = ArgumentsEncryptionUtils.encryptToJson(
            user,
            usersToDisplay.stream()
                    .filter(prdct -> prdct.getLogin().equals(login))
                    .findFirst()
                    .get()
        );
        editUser.setUsersJson(usersJson);
        return editUser;
    }

    @OnEvent(component = "insertUserLink")
    Object onInsertUser() {
        insertUserPage.setUserJson(ArgumentsEncryptionUtils.encryptToJson(user));
        return insertUserPage;
    }

}
