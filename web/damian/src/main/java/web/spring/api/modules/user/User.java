package web.spring.api.modules.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import web.spring.api.modules.notification.Notification;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "User")
@Data
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic
    @Column(name = "login", nullable = false, length = 24)
    private String login;
    @Basic
    @Column(name = "password", nullable = false)
    private String password;
    @Basic
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;
    @Basic
    @Column(name = "mobile_app_access", nullable = false)
    private boolean mobileAppAccess;
    @Basic
    @Column(name = "modify_ntf_addr", nullable = false)
    private boolean modifyNtfAddr;
    @Basic
    @Column(name = "modify_ntf_content", nullable = false)
    private boolean modifyNtfContent;
    @Basic
    @Column(name = "modify_ntf_assiuser", nullable = false)
    private boolean modifyNtfAssiuser;

    @OneToMany(mappedBy = "notifiedLogin", fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @ToString.Exclude
    private List<Notification> userNotifications;

    @OneToMany(mappedBy = "assignedLogin", fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @ToString.Exclude
    private List<Notification> notificationsAssigned;

    public User(){
        this.login = "";
        this.password = "";
        this.isAdmin = false;
        this.mobileAppAccess = false;
        this.modifyNtfAddr = false;
        this.modifyNtfContent = false;
        this.modifyNtfAssiuser = false;
        this.userNotifications = List.of();
        this.notificationsAssigned= List.of();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isAdmin = false;
        this.mobileAppAccess = false;
        this.modifyNtfAddr = false;
        this.modifyNtfContent = false;
        this.modifyNtfAssiuser = false;
        this.userNotifications = List.of();
        this.notificationsAssigned= List.of();
    }
}
