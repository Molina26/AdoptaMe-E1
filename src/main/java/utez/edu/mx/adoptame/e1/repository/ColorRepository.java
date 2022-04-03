package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
