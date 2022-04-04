package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    List<Color> findAllColors();

    Long countAllColors();

    Optional<Color> findColorById(Long id);
}
