package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;

@Repository
public interface AdoptionApplicationRepository extends PagingAndSortingRepository<AdoptionApplication, Long> {

    Page<AdoptionApplication> findAllByUser(UserAdoptame user, Pageable pageable);

}
