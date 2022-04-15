package utez.edu.mx.adoptame.e1.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.service.BlogServiceImpl;

@Controller
public class MainController {
    private final BlogServiceImpl blogService;

    public MainController(BlogServiceImpl blogService){
        this.blogService = blogService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Blog> blogs = blogService.findAllByIsPrincipal(true);
        boolean flagRegister = (blogs.size() > 0) ?  true : false;

        model.addAttribute("listBlogs",blogs);
        model.addAttribute("flagRegister",flagRegister);
        return "index";
    }
}
