package utez.edu.mx.AdoptaMe_E1.service;

import utez.edu.mx.AdoptaMe_E1.entity.Personality;

import java.util.List;
import java.util.Optional;

public interface PersonalityService {
    List<Personality> findAllPersonalities();

    Long countAllPersonalities();

    Optional<Personality> findPersonalityById(Long id);
}
