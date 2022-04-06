package utez.edu.mx.adoptame.e1.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.model.request.blog.BlogInsertDto;
import java.util.Optional;


public interface BlogService {
     Page<Blog> findAllBlog(Pageable pageable);
     Optional<Blog> findBlogById(Long id);
     boolean saveBlog(BlogInsertDto blog, String imageName, String username);
     boolean updateBlog(Blog blog);
   
}
