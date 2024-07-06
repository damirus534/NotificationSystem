package web.spring.api.modules.notification;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import web.spring.api.modules.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "notification_id", nullable = false)
    private long notificationId;
    @Basic
    @Column(name = "date", nullable = false)
    private Timestamp date;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference(value = "user-ntf")
    @JoinColumn(name = "notified_login", nullable = false, referencedColumnName = "login")
    private User notifiedLogin;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference(value = "user-assi")
    @JoinColumn(name = "assigned_login", nullable = false, referencedColumnName = "login")
    private User assignedLogin;
    @Basic
    @Column(name = "content", nullable = false)
    private String content;
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    private String notificationIdStr;//need to tapestry action recognition (edit/delete)

    public Notification(long notificationId, Timestamp date, User notifiedLogin, User assignedLogin, String content, String address) {
        this.notificationId = notificationId;
        this.date = date;
        this.notifiedLogin = notifiedLogin;
        this.assignedLogin = assignedLogin;
        this.content = content;
        this.address = address;
        this.notificationIdStr = String.valueOf(notificationId);
    }
}
