package utez.edu.mx.AdoptaMe_E1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> findLastInsertedToAdoption(String tracingRegister, Integer limit);

    Page<Pet> findAll(Pageable pageable);

    Optional<Pet> findPetById(Long id);

    boolean create(Pet pet);

    boolean update(Pet pet);

}
