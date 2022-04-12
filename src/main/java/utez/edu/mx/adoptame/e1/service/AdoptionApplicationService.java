package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;

public interface AdoptionApplicationService {

    Page<AdoptionApplication> findAllAdoptionApplications(Pageable pageable);

    Page<AdoptionApplication> findAdoptionApplicationsByUsername(String username, Pageable pageable);


    boolean createApplication(AdoptionRegisterDto adoptionRegisterDto);
}
