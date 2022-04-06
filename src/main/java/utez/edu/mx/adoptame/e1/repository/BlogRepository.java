package utez.edu.mx.adoptame.e1.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.adoptame.e1.entity.Blog;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog,Long> {
    
}
