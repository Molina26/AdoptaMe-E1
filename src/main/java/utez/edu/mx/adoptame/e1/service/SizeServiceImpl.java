package utez.edu.mx.adoptame.e1.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.adoptame.e1.entity.Size;
import utez.edu.mx.adoptame.e1.repository.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService{

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Size> findAllSizes() {
        return sizeRepository.findAll(Sort.by("name"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAllSizes() {
        return sizeRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Size> findSizeById(Long id) {
        return sizeRepository.findById(id);
    }
}
