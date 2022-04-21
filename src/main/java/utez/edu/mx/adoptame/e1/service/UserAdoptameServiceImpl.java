package utez.edu.mx.adoptame.e1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.MovementManagement;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.entity.Role;
import utez.edu.mx.adoptame.e1.model.request.blog.BlogUpdateDto;
import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utez.edu.mx.adoptame.e1.model.request.user.UserUpdateDto;
import utez.edu.mx.adoptame.e1.repository.DetailUserInfoRepository;
import utez.edu.mx.adoptame.e1.repository.RolRepository;
import utez.edu.mx.adoptame.e1.repository.UserAdoptameRepository;
import utez.edu.mx.adoptame.e1.util.InfoMovement;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

@Service
public class UserAdoptameServiceImpl implements UserAdoptameService {

    private final Validator validator;

    private final Logger logger = LoggerFactory.getLogger(UserAdoptameServiceImpl.class);
    private final UserAdoptameRepository userAdoptameRepository;
    private final RolRepository rolRepository;
    private final DetailUserInfoRepository detailUserInfoRepository;
    private final InfoMovement infoMovement;
    private final MovementManagementServiceImpl movementManagementService;
    private final PasswordEncoder passwordEncoder;

    public UserAdoptameServiceImpl(
            UserAdoptameRepository userAdoptameRepository, Validator validator, RolRepository rolRepository,
            DetailUserInfoRepository detailUserInfoRepository, InfoMovement infoMovement,
            MovementManagementServiceImpl movementManagementService,
            PasswordEncoder passwordEncoder) {
        this.userAdoptameRepository = userAdoptameRepository;
        this.validator = validator;
        this.rolRepository = rolRepository;
        this.detailUserInfoRepository = detailUserInfoRepository;
        this.infoMovement = infoMovement;
        this.movementManagementService = movementManagementService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAdoptame findUserByUsername(String username) {
        return userAdoptameRepository.findUserByUsername(username);
    }

    @Override
    public Optional <DetailUserinfo> findDetailsUserInfo(UserAdoptame user) {
        Optional <DetailUserinfo> detail = detailUserInfoRepository.findDetailUserinfoByUser(user);
        if(detail.isPresent()){
            return detail;
        }
        return Optional.empty();
    }

    @Override
    public boolean saveUser(UserInsertDto userDto) {
        boolean validInsert = false;

        Optional<Role> rol = rolRepository.findRolByName(userDto.getRole().getName());

        if (rol.isPresent()) {
            if (rol.get().getName().equals("ROLE_ADOPTADOR") || rol.get().getName().equals("ROLE_VOLUNTARIO")) {
                UserAdoptame user = new UserAdoptame();
                DetailUserinfo details = new DetailUserinfo();

                String passwordEcrypt = passwordEncoder.encode(userDto.getPassword());
                userDto.setPassword(passwordEcrypt);

                BeanUtils.copyProperties(userDto, user);
                BeanUtils.copyProperties(userDto, details);

                user.setEnabled(true);
                user.getRoles().add(rol.get());
                details.setUser(user);

                try {
                    UserAdoptame userInsertedBd = userAdoptameRepository.save(user);


                    if (userInsertedBd.getId() != 0) {
                        detailUserInfoRepository.save(details);
                        validInsert = true;    
                    }
                } catch (Exception e) {
                    logger.error("error to insert userAdoptame");
                }
            }
        }

        return validInsert;
    }

    @Override
    public Optional<UserAdoptame> findUserById(Long id) {
        return userAdoptameRepository.findById(id);
    }

    public Map<String, List<String>> getValidationInsertUserAdoptame(UserInsertDto userDto) {
        Set<ConstraintViolation<UserInsertDto>> violations = validator.validate(userDto);
        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserInsertDto> error : violations) {
                List<String> messages = new ArrayList<>();
                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();
                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messages.add(message);
                    errors.put(key, messages);
                }
            }
        }
        return errors;
    }

    @Override
    public boolean saveUser(UserAdoptame user) {
        UserAdoptame userAdoptame = userAdoptameRepository.save(user);
        if(userAdoptame.getId() != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserUpdateDto userUpdateDto) {
        boolean validUpdate = false;
        Optional<UserAdoptame> userDataRegistered =  userAdoptameRepository.findById(userUpdateDto.getId());

        if(userDataRegistered.isPresent()){
            Optional<DetailUserinfo> detailsUserInfoRegistered =  detailUserInfoRepository.findDetailUserinfoByUser(userDataRegistered.get());
            if(detailsUserInfoRegistered.isPresent()){

                try{
                    BeanUtils.copyProperties(userUpdateDto, userDataRegistered.get());
                    BeanUtils.copyProperties(userUpdateDto, detailsUserInfoRegistered.get());
                    UserAdoptame userSuccess = userAdoptameRepository.save(userDataRegistered.get());
                    if(userSuccess.getId() != 0){
                        DetailUserinfo successDetails =  detailUserInfoRepository.save(detailsUserInfoRegistered.get());
                        validUpdate = true;
                        MovementManagement movement = new MovementManagement();

                        movement.setModuleName(infoMovement.getModuleName());
                        movement.setUsername(infoMovement.getUsername());
                        movement.setAction(infoMovement.getActionMovement());
                        movement.setMovementDate(new Date());
                        movement.setPreviousData(userDataRegistered.get().toString() + "  "  + detailsUserInfoRegistered.get().toString());
                        movement.setNewData(userSuccess.toString() +  "   " + successDetails.toString());
                        movementManagementService.createOrUpdate(movement);
                    }
                }catch (Exception e){
                    logger.error("error to update a user");
                }


            }
        }

        return validUpdate;
    }

    public Map<String, List<String>> getValidationToUpdateUser(UserUpdateDto userUpdateDto) {
        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(userUpdateDto);
        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserUpdateDto> error : violations) {

                List<String> messages = new ArrayList<>();

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();
                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messages.add(message);
                    errors.put(key, messages);
                }
            }
        }
        return errors;
    }
}