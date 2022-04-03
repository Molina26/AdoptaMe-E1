package utez.edu.mx.adoptame.e1.service;

import utez.edu.mx.adoptame.e1.entity.Size;

import java.util.List;
import java.util.Optional;

public interface SizeService {
    List<Size> findAllSizes();

    Long countAllSizes();

    Optional<Size> findSizeById(Long id);
}
