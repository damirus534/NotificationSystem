package web.pages.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.springframework.data.util.Pair;
import web.spring.api.modules.notification.Notification;
import web.spring.api.modules.user.User;
import web.spring.utils.routing.ArgumentsEncryptionUtils;

public class EditNotification {

    @Getter
    @Setter
    @PageActivationContext
    private String entryJson; //user,notification

    private User user;

    @Property
    private boolean hasPermission;

    void onActivate() {
        hasPermission = true;
        Pair<User, Notification> pageEntries = ArgumentsEncryptionUtils
                .decryptToObjectPair(entryJson,User.class, Notification.class);
        if (pageEntries == null || pageEntries.getFirst() == null || pageEntries.getSecond() == null){
            hasPermission = false;
        }else{
            user = pageEntries.getFirst();
        }
    }

}
