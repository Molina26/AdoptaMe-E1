package utez.edu.mx.AdoptaMe_E1.controller;



import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.AdoptaMe_E1.entity.Blog;
import utez.edu.mx.AdoptaMe_E1.service.BlogServiceImpl;

import java.util.Date;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    BlogServiceImpl blogServiceImpl;

    @Secured({"ROLE_ADMINISTRADOR"})
    @GetMapping("")
    public String findAllBlog(Model model){
        model.addAttribute("listBlog", blogServiceImpl.findAllBlog());
        return  "views/auth/blog/blogList";
    }


    @GetMapping("/is-principal")
    public String findAllBlogByIsPrincipal(Model model){
        model.addAttribute("", "");
        return "";
    }
    

    @GetMapping("/specific/{id}")
    public String findBlogById(@PathVariable("id") Long id, Model model){
       model.addAttribute("blog",  blogServiceImpl.findBlogById(id));
        return "";
    }


    @Secured({"ROLE_ADMINISTRADOR"})
    @GetMapping("/created")
    public String createdBlog(Blog blog, Model model ){
        model.addAttribute("blog", blog);
        return "views/auth/blog/blogForm";
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    @PostMapping("/save")
    public String saveBlog(Blog blog, BindingResult result, Model model, @RequestParam("image") MultipartFile multipartFileBlog){


        blog.setImage(multipartFileBlog.getOriginalFilename());

        blog.setCreatedAt(new Date());
        blog.setUser();

        blogServiceImpl.saveBlog(blog);
        return "views/auth/blog/blogList";
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    @PostMapping("/delete")
    public String deleteBlogById(@RequestParam("id") Long id, Model model){
        blogServiceImpl.deleteBlogById(id);
        return "";
    }
}
