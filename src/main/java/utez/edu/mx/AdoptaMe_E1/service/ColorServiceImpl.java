package utez.edu.mx.AdoptaMe_E1.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.AdoptaMe_E1.entity.Color;
import utez.edu.mx.AdoptaMe_E1.repository.ColorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService{

    private final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Color> findAllColors() {
        return colorRepository.findAll(Sort.by("name"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAllColors() {
        return colorRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Color> findColorById(Long id) {
        return colorRepository.findById(id);
    }
}
