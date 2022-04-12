package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;

import java.util.Optional;

public interface UserAdoptameService {
    boolean createAccount(DetailUserinfo user);

    Optional<UserAdoptame> findUserById(Long id);

    UserAdoptame findUserByUsername(String username);
}
