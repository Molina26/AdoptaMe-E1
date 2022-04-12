package utez.edu.mx.adoptame.e1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.repository.UserAdoptameRepository;

import java.util.Optional;

@Service
public class UserAdoptameServiceImpl implements  UserAdoptameService {

    @Autowired
    private UserAdoptameRepository userAdoptameRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean createAccount(DetailUserinfo user) {

        String passwordEncoded = passwordEncoder.encode(user.getUser().getPassword());

        UserAdoptame userToInsert = user.getUser();


        return false;
    }

    @Override
    public Optional<UserAdoptame> findUserById(Long id) {
        return userAdoptameRepository.findById(id);
    }

    @Override
    public UserAdoptame findUserByUsername(String username) {
        return userAdoptameRepository.findUserByUsername(username);
    }
}
