package utez.edu.mx.AdoptaMe_E1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;

import java.util.List;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long>{

//    @Query(value = "SELECT p FROM Pet p WHERE p.availableAdoption = true AND p.isAcepted = ?1 ORDER BY p.createdAt DESC LIMIT ?limit", nativeQuery = true)
//    List<Pet> findLastInsertedToAdoptionWithLimit(String tracingRegister, Integer limit);
}
