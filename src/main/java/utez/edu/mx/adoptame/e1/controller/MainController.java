package utez.edu.mx.adoptame.e1.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.service.BlogServiceImpl;
import utez.edu.mx.adoptame.e1.service.PetServiceImpl;

@Controller
public class MainController {
    private final BlogServiceImpl blogService;
    private final PetServiceImpl petService;

    public MainController(BlogServiceImpl blogService, PetServiceImpl petService){
        this.blogService = blogService;
        this.petService = petService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Blog> blogs = blogService.findAllByIsPrincipal(true);
        boolean flagRegister = (blogs.size() > 0) ?  true : false;

        model.addAttribute("listBlogs",blogs);
        model.addAttribute("flagRegister",flagRegister);

        model.addAttribute("listPets" , petService.findTop9ByOrderByCreatedAtDesc());

        return "index";
    }
}
