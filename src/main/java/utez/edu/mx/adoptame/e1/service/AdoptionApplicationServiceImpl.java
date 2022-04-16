package utez.edu.mx.adoptame.e1.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.entity.MovementManagement;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.enums.StateAdoptionApplication;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionUpdateDto;
import utez.edu.mx.adoptame.e1.repository.AdoptionApplicationRepository;
import utez.edu.mx.adoptame.e1.util.InfoMovement;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.*;

@Service
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final AdoptionApplicationRepository adoptionApplicationRepository;

    private final PetServiceImpl petService;

    private final UserAdoptameServiceImpl userAdoptameService;

    private final MovementManagementServiceImpl movementManagementService;

    private final InfoMovement infoMovement;

    @Autowired
    private Validator validator;

    public AdoptionApplicationServiceImpl(AdoptionApplicationRepository adoptionApplicationRepository,
                                          PetServiceImpl petService,
                                          UserAdoptameServiceImpl userService,
                                          MovementManagementServiceImpl movementManagementService,
                                          InfoMovement infoMovement) {
        this.adoptionApplicationRepository = adoptionApplicationRepository;
        this.petService = petService;
        this.userAdoptameService = userService;
        this.movementManagementService = movementManagementService;
        this.infoMovement = infoMovement;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdoptionApplication> findAllAdoptionApplications(Pageable pageable) {
        return adoptionApplicationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdoptionApplication> findAdoptionApplicationsByUsername(String username, Pageable pageable) {
        return adoptionApplicationRepository.findAllByUser(username, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdoptionApplication> findAdoptionApplicationId(Long id) {
        return adoptionApplicationRepository.findById(id);
    }

    @Override
    public boolean createApplication(AdoptionRegisterDto adoptionRegisterDto) {
        boolean flag = false;

        UserAdoptame user = userAdoptameService.findUserByUsername(adoptionRegisterDto.getUsername());

        Optional<Pet> petToAdopt = petService.findPetById(adoptionRegisterDto.getPetId());

        if (petToAdopt.isPresent() && user != null){
            this.changeStatePetAdoption(petToAdopt.get().getId(), false);

            AdoptionApplication adoptionApplicationToInsert = new AdoptionApplication();
            adoptionApplicationToInsert.setApplicationDate(new Date());
            adoptionApplicationToInsert.setState("pendiente");
            adoptionApplicationToInsert.setPet(petToAdopt.get());
            adoptionApplicationToInsert.setUser(user);

            AdoptionApplication adoptionInserted = adoptionApplicationRepository.save(adoptionApplicationToInsert);

            if (adoptionInserted.getId() != 0) {
                flag = true;
                MovementManagement movement = new MovementManagement();
                movement.setModuleName(infoMovement.getModuleName());
                movement.setUsername(infoMovement.getUsername());
                movement.setMovementDate(new Date());
                movement.setAction(infoMovement.getActionMovement());
                movement.setNewData(adoptionInserted.toString());
                movementManagementService.createOrUpdate(movement);

            }

        }

        return flag;
    }

    @Override
    public boolean changeStateAdoption(AdoptionUpdateDto adoptionUpdateDto) {
        boolean flag = false;

        Optional<AdoptionApplication> adoptionApplicationPreview = adoptionApplicationRepository.findById(adoptionUpdateDto.getId());

        if (adoptionApplicationPreview.isPresent()) {
            AdoptionApplication adoptionApplicationToUpdate = new AdoptionApplication();

            BeanUtils.copyProperties(adoptionApplicationPreview.get(), adoptionApplicationToUpdate);

            adoptionApplicationToUpdate.setClosedDate(new Date());
            adoptionApplicationToUpdate.setState(adoptionUpdateDto.getState());

            if (adoptionApplicationToUpdate.getState().equals(StateAdoptionApplication.rechazada.toString())){
                this.changeStatePetAdoption(adoptionApplicationPreview.get().getPet().getId(), true);
            }

            AdoptionApplication adoptionApplicationUpdated = adoptionApplicationRepository.save(adoptionApplicationToUpdate);

            if (adoptionApplicationUpdated.getId() != 0) {
                MovementManagement movement = new MovementManagement();
                movement.setModuleName(infoMovement.getModuleName());
                movement.setMovementDate(new Date());
                movement.setUsername(infoMovement.getUsername());
                movement.setAction(infoMovement.getActionMovement());
                movement.setNewData(adoptionApplicationUpdated.toString());
                movement.setPreviousData(adoptionApplicationPreview.get().toString());
                movementManagementService.createOrUpdate(movement);

                flag = true;
            }

        }

        return flag;
    }

    private void changeStatePetAdoption(Long petId, Boolean value){

        Optional<Pet> petPrevious = petService.findPetById(petId);

        if (petPrevious.isPresent()) {
            boolean changeStateAdoptionPet = petService.changeAvailableAdoptionPet(petId, value);

            if (changeStateAdoptionPet) {
                Optional<Pet> petUpdated = petService.findPetById(petId);


                if (petUpdated.isPresent()) {
                    MovementManagement movement = new MovementManagement();
                    movement.setModuleName(infoMovement.getModuleName());
                    movement.setUsername(infoMovement.getUsername());
                    movement.setMovementDate(new Date());
                    movement.setAction("UPDATE");
                    movement.setPreviousData(petPrevious.get().toString());
                    movement.setNewData(petUpdated.get().toString());

                    movementManagementService.createOrUpdate(movement);
                }



            }
        }

    }

    public Map<String, List<String>> getValidationToCreateAdoptionAplication(AdoptionRegisterDto adoptionRegisterDto) {
        Set<ConstraintViolation<AdoptionRegisterDto>> violations = validator.validate(adoptionRegisterDto);

        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {

            for (ConstraintViolation<AdoptionRegisterDto> error : violations) {

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                List<String> messagesToAcceptOrReject = new ArrayList<>();

                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {

                    messagesToAcceptOrReject.add(message);
                    errors.put(key, messagesToAcceptOrReject);
                }

            }
        }
        return errors;
    }

    public Map<String, List<String>> getValidationToChangeStateAdoptionAplication(AdoptionUpdateDto adoptionUpdateDto) {
        Set<ConstraintViolation<AdoptionUpdateDto>> violations = validator.validate(adoptionUpdateDto);

        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {

            for (ConstraintViolation<AdoptionUpdateDto> error : violations) {

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    List<String> messagesToAcceptOrReject = new ArrayList<>();
                    messagesToAcceptOrReject.add(message);
                    errors.put(key, messagesToAcceptOrReject);
                }

            }
        }
        return errors;
    }
}
