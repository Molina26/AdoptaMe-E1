package utez.edu.mx.adoptame.e1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "size")
@Data
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
}
