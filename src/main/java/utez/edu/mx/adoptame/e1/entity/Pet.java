package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pet")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "age", nullable = false, length = 40)
    private String age;

    @Column(name = "sex", nullable = false, columnDefinition = "ENUM('macho','hembra')")
    private String sex;

    @Column(name = "type", nullable = false, columnDefinition = "ENUM('perro','gato')")
    private String type;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(name = "available_adoption", nullable = false)
    private Boolean availableAdoption;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "ENUM('pendiente','aceptado', 'rechazado')")
    private String isAccepted;

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

    @Override
    public String toString() {
        return "Pet: {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", availableAdoption=" + availableAdoption +
                ", isAccepted='" + isAccepted + '\'' +
                ", createdAt=" + createdAt +
                ", size=" + size +
                ", color=" + color +
                ", personality=" + personality +
                '}';
    }
}
