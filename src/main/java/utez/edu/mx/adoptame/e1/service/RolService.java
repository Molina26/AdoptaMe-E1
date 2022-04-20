package utez.edu.mx.adoptame.e1.service;

import java.util.List;
import java.util.Optional;

import utez.edu.mx.adoptame.e1.entity.Role;

public interface RolService {
    List<Role> findAllRol();

    Optional<Role> findRolByName(String name);

}
