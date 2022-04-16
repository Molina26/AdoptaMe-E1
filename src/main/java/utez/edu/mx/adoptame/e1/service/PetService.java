package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.model.request.pet.PetInsertDto;
import utez.edu.mx.adoptame.e1.model.request.pet.PetSearchDto;
import utez.edu.mx.adoptame.e1.model.request.pet.PetTracingRegisterDto;
import utez.edu.mx.adoptame.e1.model.request.pet.PetUpdateDto;

import java.util.Optional;

public interface PetService {

    Page<Pet> findAll(Pageable pageable);

    Page<Pet> findPetsToAdopt(String type, String isAccepted, Pageable pageable);

    Page<Pet> findPetsByColorSizeOrPersonality(PetSearchDto petSearchDto, Pageable pageable);

    Optional<Pet> findPetById(Long id);

    boolean create(PetInsertDto pet, String imageName);

    boolean update(PetUpdateDto pet);

    boolean acceptOrRejectPet(PetTracingRegisterDto pet);

    boolean changeAvailableAdoptionPet(Long id, Boolean AvailableAdoptionPet);

}
