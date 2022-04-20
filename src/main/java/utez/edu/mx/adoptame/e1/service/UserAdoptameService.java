package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;

import java.util.Optional;

import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;

public interface UserAdoptameService {
    boolean saveUser(UserInsertDto user);

    Optional<UserAdoptame> findUserById(Long id);

    UserAdoptame findUserByUsername(String username);

    boolean saveUser(UserAdoptame user);
    Optional <DetailUserinfo> findDetailsUserInfo(UserAdoptame user);

}
