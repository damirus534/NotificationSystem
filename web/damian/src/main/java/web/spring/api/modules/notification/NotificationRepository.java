package web.spring.api.modules.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.spring.api.modules.user.User;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long>  {

    List<Notification> findAllByNotifiedLogin(User notifiedLogin);

    List<Notification> findByAssignedLoginLogin(String assignedLogin);
}
