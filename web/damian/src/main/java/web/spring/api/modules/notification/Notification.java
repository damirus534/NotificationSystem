package web.spring.api.modules.notification;

import lombok.Data;
import web.spring.api.modules.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Notifications")
@Data
public class Notification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "notification_id", nullable = false)
    private long notificationId;
    @Basic
    @Column(name = "date", nullable = false)
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "notified_login", nullable = false, referencedColumnName = "login")
    private User notifiedLogin;
    @ManyToOne
    @JoinColumn(name = "assigned_login", nullable = false, referencedColumnName = "login")
    private User assignedLogin;
    @Basic
    @Column(name = "content", nullable = false)
    private String content;
    @Basic
    @Column(name = "address", nullable = false)
    private String address;
}
