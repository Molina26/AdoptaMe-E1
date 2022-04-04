package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "blog")
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 70)
    /*@NotBlank(message = "El Titulo no puede estar solo con espacios en blanco")
    @NotEmpty(message = "El Titulo no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "No debe tener caracteres especiales")
    @Size(min=2, max=70, message = "El nombre de la mascota no debe ser mayor a 50 caracteres")*/
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_principal", nullable = false)
    private Boolean isPrincipal;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAdoptame user;
}
