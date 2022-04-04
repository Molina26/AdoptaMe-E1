package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "detail_userinfo")
@Data
public class DetailUserinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "address", nullable = false, length = 750)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAdoptame user;
}
