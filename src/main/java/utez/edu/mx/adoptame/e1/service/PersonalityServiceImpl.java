package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.adoptame.e1.entity.Personality;
import utez.edu.mx.adoptame.e1.repository.PersonalityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalityServiceImpl implements PersonalityService{

    private final PersonalityRepository personalityRepository;

    public PersonalityServiceImpl(PersonalityRepository personalityRepository) {
        this.personalityRepository = personalityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personality> findAllPersonalities() {
        return personalityRepository.findAll(Sort.by("name"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAllPersonalities() {
        return personalityRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personality> findPersonalityById(Long id) {
        return personalityRepository.findById(id);
    }
}
