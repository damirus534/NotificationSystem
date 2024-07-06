package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
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

    @Component
    private Form editForm;

    @Property
    private boolean isAdmin;
    @Property
    private boolean hasMobileAccess;

    private UserService userService = SpringUtils.ctx.getBean(UserService.class);

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    void onActivate() {
        try{
            Pair<User, User> pageEntries = ArgumentsEncryptionUtils
                    .decryptToObjectPair(usersJson,User.class, User.class);
            isAdmin = pageEntries.getSecond().isAdmin();
            hasMobileAccess = pageEntries.getSecond().isMobileAppAccess();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    void onSuccessFromEditForm() {
        logger.info("success", user.toString());
        logger.info("success value", isAdmin);
    }


}