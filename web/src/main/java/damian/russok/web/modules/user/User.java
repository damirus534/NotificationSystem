package damian.russok.web.modules.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User", schema = "mydb", catalog = "")
@Data
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic
    @Column(name = "login", nullable = false, length = 45)
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
}
