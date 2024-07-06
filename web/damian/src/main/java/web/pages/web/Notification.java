package web.pages.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import web.spring.api.modules.user.User;


public class Notification {

    @Getter
    @Setter
    @PageActivationContext
    private String userJson;

    @Property
    private boolean hasPermission;

    private User user;

    void onActivate() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userJson, User.class);
            hasPermission = true;
        } catch (Exception e) {
            hasPermission = false;
        }
    }


}
