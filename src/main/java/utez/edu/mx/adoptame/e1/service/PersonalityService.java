package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.Personality;

import java.util.List;
import java.util.Optional;

public interface PersonalityService {
    List<Personality> findAllPersonalities();

    Long countAllPersonalities();

    Optional<Personality> findPersonalityById(Long id);
}
