package utez.edu.mx.AdoptaMe_E1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.AdoptaMe_E1.entity.Blog;
import utez.edu.mx.AdoptaMe_E1.repository.BlogRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.Override;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogRepository blogRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findAllBlog(){
        return blogRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findAllBlogByIsPrincipal(){
        return null;
    }

    @Override
    public Blog findBlogById(Long id){
        try {
            return blogRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
       
    }

    @Override
    public Boolean saveBlog(Blog blog){
        Blog blogRegister = blogRepository.save(blog);
        if(blogRegister != null){
            return true;
        }
        return false;
    }

    
    @Override
    public Boolean deleteBlogById(Long id){
        Blog blogDelete = blogRepository.findById(id).get();
        if(blogDelete != null){
            blogDelete.setIsPrincipal(!blogDelete.getIsPrincipal());
            blogRepository.save(blogDelete);
            return true;
        }
       return false;
   
    }
}
