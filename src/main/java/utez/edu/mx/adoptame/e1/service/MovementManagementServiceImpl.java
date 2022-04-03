package utez.edu.mx.adoptame.e1.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.adoptame.e1.entity.MovementManagement;
import utez.edu.mx.adoptame.e1.repository.MovementManagementRepository;

@Service
public class MovementManagementServiceImpl implements MovementManagementService{

    private final MovementManagementRepository movementManagementRepository;

    public MovementManagementServiceImpl(MovementManagementRepository repository) {
        this.movementManagementRepository = repository;
    }

    @Override
    public void createOrUpdate(MovementManagement movementManagement) {

        movementManagementRepository.save(movementManagement);
    }
}
