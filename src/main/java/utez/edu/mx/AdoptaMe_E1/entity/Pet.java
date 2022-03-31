package utez.edu.mx.AdoptaMe_E1.entity;

import lombok.Data;
import utez.edu.mx.AdoptaMe_E1.annotation.ColorAccepted;
import utez.edu.mx.AdoptaMe_E1.annotation.PersonalityAccepted;
import utez.edu.mx.AdoptaMe_E1.annotation.SizeAccepted;
import utez.edu.mx.AdoptaMe_E1.annotation.ValueOfEnumAccepted;
import utez.edu.mx.AdoptaMe_E1.enums.Sex;
import utez.edu.mx.AdoptaMe_E1.enums.TracingRegisterPet;
import utez.edu.mx.AdoptaMe_E1.enums.TypePet;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "pet")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    @NotEmpty(message = "Debe de indicar el nombre de la mascota")
    @Pattern(regexp = "[a-zA-Z ]{3,250}", message = "Valor no aceptado")
    private String name;

    @Column(name = "age", nullable = false, length = 40)
    @NotEmpty(message = "Debe indicar la edad de la mascota")
    @Pattern(regexp = "[a-zA-Z0-9 ]{1,40}", message = "Valor no aceptado")
    private String age;

    @Column(name = "sex", nullable = false, columnDefinition = "ENUM('macho','hembra')")
    @ValueOfEnumAccepted(enumClass = Sex.class, message = "Este valor no es aceptado para el sexo")
    private String sex;

    @Column(name = "type", nullable = false, columnDefinition = "ENUM('perro','gato')")
    @ValueOfEnumAccepted(enumClass = TypePet.class)
    private String type;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(name = "available_adoption", nullable = false)
    private Boolean availableAdoption;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "ENUM('pendiente','aceptado', 'rechazado')")
    @ValueOfEnumAccepted(enumClass = TracingRegisterPet.class)
    private String isAccepted;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    @SizeAccepted(message = "Este tamaño no es aceptado")
    @NotNull
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    @ColorAccepted(message = "Este color no es aceptado")
    @NotNull
    private Color color;

    @ManyToOne
    @JoinColumn(name = "personality_id", nullable = false)
    @PersonalityAccepted(message = "Este caracter no es aceptado")
    @NotNull
    private Personality personality;
}
