package utez.edu.mx.AdoptaMe_E1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "session_control_access")
@Data
@NoArgsConstructor
public class SessionControlAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 250)
    private String username;

    @Column(name = "is_actual_session", nullable = false)
    private Boolean isActualSession;

    @Column(name = "access_date", nullable = false)
    private Date accessDate;

    @Column(name = "logout_date")
    private Date logoutDate;

    public SessionControlAccess(String username, Boolean isActualSession, Date accessDate) {
        this.username = username;
        this.isActualSession = isActualSession;
        this.accessDate = accessDate;
    }
}
