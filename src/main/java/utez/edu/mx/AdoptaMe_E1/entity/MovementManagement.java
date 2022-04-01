package utez.edu.mx.AdoptaMe_E1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movements_management")
@Data
public class MovementManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name", length = 50, nullable = false)
    private String moduleName;

    @Column(name = "username", length = 250, nullable = false)
    private String username;

    @Column(name = "action", length = 20, nullable = false)
    private String action;

    @Column(name = "movement_date", nullable = false)
    private Date movementDate;

    @Column(name = "previous_data", columnDefinition = "TEXT")
    private String previousData;

    @Column(name = "new_data", columnDefinition = "TEXT")
    private String newData;

}
