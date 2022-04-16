package utez.edu.mx.adoptame.e1.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import utez.edu.mx.adoptame.e1.entity.MovementManagement;
import utez.edu.mx.adoptame.e1.entity.Rol;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import utez.edu.mx.adoptame.e1.repository.RolRepository;
import utez.edu.mx.adoptame.e1.repository.UserAdoptameRepository;
import utez.edu.mx.adoptame.e1.util.InfoMovement;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

@Service
public class UserAdoptameServiceImpl implements UserAdoptameService{
    
    private final Validator validator;

    private final Logger logger = LoggerFactory.getLogger(UserAdoptameServiceImpl.class);
    private final UserAdoptameRepository userAdoptameRepository;
    private final RolRepository rolRepository;
    private final MovementManagementServiceImpl movementManagementService;
    private final InfoMovement infoMovement;
    private final PasswordEncoder passwordEncoder;
    
    public UserAdoptameServiceImpl( MovementManagementServiceImpl movementManagementService, InfoMovement infoMovement,
                           UserAdoptameRepository userAdoptameRepository, Validator validator, RolRepository rolRepository, PasswordEncoder passwordEncoder){
        this.movementManagementService = movementManagementService;
        this.infoMovement = infoMovement;
        this.userAdoptameRepository = userAdoptameRepository;
        this.validator = validator;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserAdoptame findUserByUsername(String username) {

        return userAdoptameRepository.findUserByUsername(username);
    }


    @Override
    public boolean saveUser(UserInsertDto userDto) {
        boolean validInsert = false;

        Optional<Rol> rol = rolRepository.findById(userDto.getRol().getId());
       
        if(rol.isPresent()){
            if(rol.get().getName().equals("ROLE_ADOPTADOR") || rol.get().getName().equals("ROLE_VOLUNTARIO")){
                UserAdoptame user = new UserAdoptame();

                String passwordEcrypt = passwordEncoder.encode(userDto.getPassword());
                userDto.setPassword(passwordEcrypt);

                BeanUtils.copyProperties(userDto, user);
                user.setEnabled(true);
                try{
                    UserAdoptame userInsertedBd = userAdoptameRepository.save(user);

                    if(userInsertedBd.getId() != 0){
                        validInsert = true;

                        MovementManagement movement = new MovementManagement();
    
                        movement.setModuleName(infoMovement.getModuleName());
                        movement.setUsername(infoMovement.getUsername());
                        movement.setAction(infoMovement.getActionMovement());
                        movement.setMovementDate(new Date());
                        movement.setNewData(userInsertedBd.toString());
    
                        movementManagementService.createOrUpdate(movement);
                    }
                }catch (Exception e){
                    logger.error("error to insert userAdoptame");
                }
            }
        }

        return validInsert;
    }


    public Map<String, List<String>> getValidationInsertUserAdoptame(UserInsertDto userDto){
        Set<ConstraintViolation<UserInsertDto>> violations = validator.validate(userDto);
        Map<String, List<String>> errors = new HashMap<>();

        if(!violations.isEmpty()){
            for (ConstraintViolation<UserInsertDto> error: violations) {
                List<String> messages = new ArrayList<>();
                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();
                if(errors.get(key) != null){
                    errors.get(key).add(message);
                }else{
                    messages.add(message);
                    errors.put(key,messages);
                }
            }
        }
        return errors;
    }
}
