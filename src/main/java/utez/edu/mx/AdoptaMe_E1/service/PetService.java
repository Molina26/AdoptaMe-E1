package utez.edu.mx.AdoptaMe_E1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;

import java.util.List;

public interface PetService {
    List<Pet> findLastInsertedToAdoption(String tracingRegister, Integer limit);

    Page<Pet> findAllPageable(Pageable pageable);

    Page<Pet> findAll(Pageable pageable);

    boolean create(Pet pet);

    Pet findOne(Long id);
}
