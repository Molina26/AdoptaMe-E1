package utez.edu.mx.AdoptaMe_E1.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import utez.edu.mx.AdoptaMe_E1.entity.Blog;
import utez.edu.mx.AdoptaMe_E1.service.BlogServiceImpl;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    BlogServiceImpl blogServiceImpl;

    @Secured({"ROLE_ADMINISTRADOR"})
    @GetMapping("")
    public String findAllBlog(Model model){
        model.addAttribute("listBlog", blogServiceImpl.findAllBlog());
        return  "";
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
    @PostMapping("/save")
    public String saveBlog(Blog blog, Model model){
        blogServiceImpl.saveBlog(blog);
        return "";
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    @PostMapping("/delete")
    public String deleteBlogById(@RequestParam("id") Long id, Model model){
        blogServiceImpl.deleteBlogById(id);
        return "";
    }
}
