package utez.edu.mx.adoptame.e1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utez.edu.mx.adoptame.e1.entity.Role;
import utez.edu.mx.adoptame.e1.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    @Transactional
    public List<Role> findAllRol() {
        return  rolRepository.findAll();
    }

    @Override
    public Optional<Role> findRolByName(String name) {
        Optional<Role> rol  = rolRepository.findRolByName(name);
        if(rol.isPresent()){
            return rol;
        }
        return Optional.empty();
    }
}
