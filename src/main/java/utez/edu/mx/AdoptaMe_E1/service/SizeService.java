package utez.edu.mx.AdoptaMe_E1.service;

import utez.edu.mx.AdoptaMe_E1.entity.Size;

import java.util.List;
import java.util.Optional;

public interface SizeService {
    List<Size> findAllSizes();

    Long countAllSizes();

    Optional<Size> findSizeById(Long id);
}
