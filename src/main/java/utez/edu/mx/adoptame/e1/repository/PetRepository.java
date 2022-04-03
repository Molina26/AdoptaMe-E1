package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Pet;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long>{

//    @Query(value = "SELECT p FROM Pet p WHERE p.availableAdoption = true AND p.isAcepted = ?1 ORDER BY p.createdAt DESC LIMIT ?limit", nativeQuery = true)
//    List<Pet> findLastInsertedToAdoptionWithLimit(String tracingRegister, Integer limit);
}
