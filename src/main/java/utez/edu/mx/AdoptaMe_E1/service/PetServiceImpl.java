package utez.edu.mx.AdoptaMe_E1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.AdoptaMe_E1.entity.MovementManagement;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;
import utez.edu.mx.AdoptaMe_E1.repository.PetRepository;
import utez.edu.mx.AdoptaMe_E1.util.InfoMovement;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;

    private final MovementManagementServiceImpl movementManagementService;

    private final InfoMovement infoMovement;

    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    public PetServiceImpl(PetRepository repository, MovementManagementServiceImpl movementManagementService, InfoMovement infoMovement) {
        this.petRepository = repository;
        this.movementManagementService = movementManagementService;
        this.infoMovement = infoMovement;
    }

    @Override
    public List<Pet> findLastInsertedToAdoption(String tracingRegister,Integer limit) {
//        return petRepository.findLastInsertedToAdoptionWithLimit(tracingRegister, limit);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pet> findPetById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public boolean create(Pet pet) {

        boolean flagInsert = false;

        pet.setAvailableAdoption(true);
        pet.setCreatedAt(new Date());
        logger.info("pet to insert" + pet);
        try {
            Pet petInserted = petRepository.save(pet);

            if (petInserted.getId() != 0) {
                flagInsert = true;
                MovementManagement movement = new MovementManagement();

                movement.setModuleName(infoMovement.getModuleName());
                movement.setUsername(infoMovement.getUsername());
                movement.setAction(infoMovement.getActionMovement());
                movement.setMovementDate(new Date());
                movement.setNewData(petInserted.toString());

                movementManagementService.createOrUpdate(movement);
            }
        } catch (Exception ex) {
            logger.error("error to insert a pet " + ex.getMessage());
        }

        return flagInsert;
    }

    @Override
    public boolean update(Pet pet) {
        boolean flagInsert = false;

        Optional<Pet> previousData = this.findPetById(pet.getId());

        if (previousData.isPresent()) {
            try {
                Pet petUpdated = petRepository.save(pet);

                if (petUpdated.getId() != 0) {
                    flagInsert = true;

                    MovementManagement movement = new MovementManagement();

                    movement.setModuleName(infoMovement.getModuleName());
                    movement.setUsername(infoMovement.getUsername());
                    movement.setAction(infoMovement.getActionMovement());
                    movement.setMovementDate(new Date());
                    movement.setPreviousData(previousData.get().toString());
                    movement.setNewData(petUpdated.toString());

                    movementManagementService.createOrUpdate(movement);
                }
            } catch (Exception ex) {

                logger.error("error to update a pet " + ex.getMessage());
            }
        }

        return flagInsert;
    }

}
