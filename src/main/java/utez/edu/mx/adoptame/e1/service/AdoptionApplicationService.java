package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionUpdateDto;

import java.util.Optional;

public interface AdoptionApplicationService {

    Page<AdoptionApplication> findAllAdoptionApplications(Pageable pageable);

    Page<AdoptionApplication> findAdoptionApplicationsByUsername(String username, Pageable pageable);

    Optional<AdoptionApplication> findAdoptionApplicationId(Long id);

    boolean createApplication(AdoptionRegisterDto adoptionRegisterDto);

    boolean changeStateAdoption(AdoptionUpdateDto adoptionUpdateDto);
}
