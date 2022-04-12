package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Pet;

import javax.transaction.Transactional;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long>{

   Page<Pet> findAllByAvailableAdoptionAndTypeAndIsAccepted(Boolean available, String type, String isAccepted, Pageable pageable);

   @Query("SELECT p FROM Pet p WHERE p.type LIKE ?1 AND p.isAccepted LIKE 'aceptado' AND (p.color.id = ?2 OR p.size.id = ?3 OR p.personality.id = ?4)")
   Page<Pet> findPetsByColorSizeOrPersonalityToAdopt(String typePet, Long colorId, Long sizeId, Long personalityId, Pageable pageable);

   @Transactional
   @Modifying
   @Query("UPDATE Pet p SET p.availableAdoption = ?2 WHERE p.id = ?1")
   int changeAvailableAdoptionPet(Long id, Boolean availableAdoption);

}
