package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "personality")
@Data
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 70)
    private String name;
}
