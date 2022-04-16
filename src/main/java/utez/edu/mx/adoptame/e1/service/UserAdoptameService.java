package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;

public interface UserAdoptameService {
    boolean saveUser(UserInsertDto user);
    UserAdoptame findUserByUsername(String username);
}
