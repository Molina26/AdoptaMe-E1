package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
}
