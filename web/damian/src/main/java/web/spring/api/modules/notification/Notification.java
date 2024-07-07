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
import java.util.ArrayList;

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
    @JoinColumn(name = "notified_login", nullable = false, referencedColumnName = "login")
    private User notifiedLogin;
    @ManyToOne
    @JoinColumn(name = "assigned_login", nullable = false, referencedColumnName = "login")
    private User assignedLogin;
    @Basic
    @Column(name = "content", nullable = false, length = 2000)
    private String content;
    @Basic
    @Column(name = "address", nullable = false)
    private String address;
}
