package utez.edu.mx.AdoptaMe_E1.service;

import utez.edu.mx.AdoptaMe_E1.entity.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    List<Color> findAllColors();

    Long countAllColors();

    Optional<Color> findColorById(Long id);
}
