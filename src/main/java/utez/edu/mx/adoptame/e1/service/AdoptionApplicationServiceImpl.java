package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;
import utez.edu.mx.adoptame.e1.repository.AdoptionApplicationRepository;

@Service
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final AdoptionApplicationRepository adoptionApplicationRepository;

    private final PetServiceImpl petService;

    public AdoptionApplicationServiceImpl(AdoptionApplicationRepository adoptionApplicationRepository,
                                          PetServiceImpl petService) {
        this.adoptionApplicationRepository = adoptionApplicationRepository;
        this.petService = petService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdoptionApplication> findAllAdoptionApplications(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdoptionApplication> findAdoptionApplicationsByUsername(String username, Pageable pageable) {
        return null;
    }

    @Override
    public boolean createApplication(AdoptionRegisterDto adoptionRegisterDto) {
        return false;
    }
}
