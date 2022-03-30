package utez.edu.mx.AdoptaMe_E1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;
import utez.edu.mx.AdoptaMe_E1.repository.PetRepository;

import java.util.Date;
import java.util.List;

@Service
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;

    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    public PetServiceImpl(PetRepository repository) {
        this.petRepository = repository;
    }

    @Override
    public List<Pet> findLastInsertedToAdoption(String tracingRegister,Integer limit) {
//        return petRepository.findLastInsertedToAdoptionWithLimit(tracingRegister, limit);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findAllPageable(Pageable pageable) {
        return petRepository.findAll(pageable);
    }


    @Override
    public Page<Pet> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean create(Pet pet) {

        boolean flagInsert = false;

        pet.setAvailableAdoption(true);
        pet.setCreatedAt(new Date());

        try {
            Pet petInserted = petRepository.save(pet);

            if (petInserted.getId() != 0) {
                flagInsert = true;
            }
        } catch (Exception ex) {

            logger.error("error to insert a pet " + ex.getMessage());
        }

        return flagInsert;
    }

    @Override
    public Pet findOne(Long id) {
        return null;
    }
}
