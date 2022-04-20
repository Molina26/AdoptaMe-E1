package utez.edu.mx.adoptame.e1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.entity.Role;
import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import utez.edu.mx.adoptame.e1.repository.DetailUserInfoRepository;
import utez.edu.mx.adoptame.e1.repository.RolRepository;
import utez.edu.mx.adoptame.e1.repository.UserAdoptameRepository;
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
    private final PasswordEncoder passwordEncoder;

    public UserAdoptameServiceImpl(
            UserAdoptameRepository userAdoptameRepository, Validator validator, RolRepository rolRepository,
            DetailUserInfoRepository detailUserInfoRepository,
            PasswordEncoder passwordEncoder) {
        this.userAdoptameRepository = userAdoptameRepository;
        this.validator = validator;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.detailUserInfoRepository = detailUserInfoRepository;
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
                logger.info("LOGER DETAILS "+ details.toString());

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
                logger.info("Error " + error.getPropertyPath().toString()+"   " +error.getMessage() );
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
}