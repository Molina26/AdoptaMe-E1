package utez.edu.mx.adoptame.e1.controller;



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
import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.service.BlogServiceImpl;

import java.util.Date;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {


}
