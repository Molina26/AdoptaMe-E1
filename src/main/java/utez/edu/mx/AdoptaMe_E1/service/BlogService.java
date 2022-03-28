package utez.edu.mx.AdoptaMe_E1.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.AdoptaMe_E1.entity.Blog;
import java.util.List;

@Service
public interface BlogService {
    public List<Blog> findAllBlog();
    public List<Blog> findAllBlogByIsPrincipal(); 
    public Blog findBlogById(Long id);
    public Boolean saveBlog(Blog blog);
    public Boolean deleteBlogById(Long id);
   
}
