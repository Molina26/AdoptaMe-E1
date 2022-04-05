package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.model.request.pet.PetInsertDto;
import utez.edu.mx.adoptame.e1.model.request.pet.PetTracingRegisterDto;
import utez.edu.mx.adoptame.e1.model.request.pet.PetUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> findLastInsertedToAdoption(String tracingRegister, Integer limit);

    Page<Pet> findAll(Pageable pageable);

    Optional<Pet> findPetById(Long id);

    boolean create(PetInsertDto pet, String imageName);

    boolean update(PetUpdateDto pet);

    boolean acceptOrRejectPet(PetTracingRegisterDto pet);

}
