package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class UserAdoptame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "fist_lastname", nullable = false, length = 150)
    private String firstLastname;

    @Column(name = "second_lastname", length = 150)
    private String secondLastname;

    @Column(name = "username", nullable = false, length = 250, unique = true)
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}