package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
