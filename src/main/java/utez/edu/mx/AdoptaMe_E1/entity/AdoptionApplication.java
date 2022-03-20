package utez.edu.mx.AdoptaMe_E1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "adoption_application")
@Data
public class AdoptionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "application_date", nullable = false)
    private Date applicationDate;

    @Column(name = "closed_date")
    private Date closedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAdoptame user;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}
