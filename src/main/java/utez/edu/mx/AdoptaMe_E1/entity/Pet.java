package utez.edu.mx.AdoptaMe_E1.entity;

import lombok.Data;
import utez.edu.mx.AdoptaMe_E1.enums.Sex;
import utez.edu.mx.AdoptaMe_E1.enums.TypePet;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypePet type;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(name = "available_adoption", nullable = false)
    private Boolean availableAdoption;

    @Column(name = "is_acepted", nullable = false)
    private Boolean isAcepted;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "personality_id", nullable = false)
    private Personality personality;
}
