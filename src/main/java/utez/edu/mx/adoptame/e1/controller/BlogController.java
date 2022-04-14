package utez.edu.mx.adoptame.e1.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.model.request.blog.BlogInsertDto;
import utez.edu.mx.adoptame.e1.model.request.blog.BlogUpdateDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.BlogService;
import utez.edu.mx.adoptame.e1.service.BlogServiceImpl;
import utez.edu.mx.adoptame.e1.util.ImageManager;
import utez.edu.mx.adoptame.e1.util.InfoMovement;
import utez.edu.mx.adoptame.e1.util.PageRender;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    private final BlogServiceImpl blogService;
    private final ImageManager imageManager;
    private final InfoMovement infoMovement;

    private final Logger logger = LoggerFactory.getLogger(BlogController.class);

    private final String MODULE_NAME = "BLOG";

    private final String MESSAGE_FILE_NOT_SELECTED = "Debe de seleccionar una imagen al marcar el blog como prinicipal";

    private final String VOWELS_AND_SPACE[] = {" ","Á","á", "é", "É", "í", "Í", "ó", "Ó", "ú", "Ú", "%"};

    public BlogController(BlogServiceImpl blogService, ImageManager imageManager,InfoMovement infoMovement){
        this.blogService = blogService;
        this.imageManager = imageManager;
        this.infoMovement = infoMovement;
    }


    @GetMapping({"/management_list", ""})
    @Secured({"ROLE_ADMINISTRADOR"})
    public String findAllBlogManagement(@RequestParam(name="page", defaultValue = "0") int page, Model model){
        int itemsByPage = 5;
        Pageable pageRequest = PageRequest.of(page, itemsByPage);
        Page<Blog> blogs = blogService.findAllBlog(pageRequest);
        PageRender<Blog> pageRender = new PageRender<>("/blog/management_list", blogs);
        model.addAttribute("listBlogs", blogs);
        model.addAttribute("page", pageRender);
        model.addAttribute("index", (itemsByPage * page));

        return "views/blog/blogList";
    }


    @GetMapping("/create")
    @Secured({"ROLE_ADMINISTRADOR"})
    public String createBlog(Model model){
        model.addAttribute("blog", new BlogInsertDto());
        return "views/blog/blogForm";
    }

   

    @PostMapping("/save")
    @Secured({"ROLE_ADMINISTRADOR"})
    public String save(@ModelAttribute("blog") BlogInsertDto blog, Authentication auth,
                       Model model, @RequestParam("imageF") MultipartFile image, RedirectAttributes flash){
        InfoToast info = new InfoToast();
     
        Map<String, List<String>> validation = blogService.getValidationInsertBlog(blog);
        boolean errorInsertImage = false;
        String imageName = "";

        infoMovement.setActionMovement("INSERT");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        if(blog.getPrincipal() && image.isEmpty()){
            logger.info("INVALIDA"); 
            model.addAttribute("errors", validation);
            model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
            model.addAttribute("blog", blog);      
            return "views/blog/blogForm";
        }

        if(!validation.isEmpty()){
            model.addAttribute("errors", validation);
            logger.info("error in validation or image empty");
            return "views/blog/blogForm";
        }

            if(!image.isEmpty()){
 
                String imageNameP  = image.getOriginalFilename()+"";

                for (int i = 0; i < VOWELS_AND_SPACE.length; i++) {
                    if(image.getSize()== 0 || image == null ){
                        break;
                    }else {
                        if(imageNameP.contains(VOWELS_AND_SPACE[i])){
                            errorInsertImage = true;
                        }
                    }
                }
    
                 imageName = (errorInsertImage ? null : imageManager.insertImage(image));
    
                if(imageName == null){
                    info.setTitle("Error de imagen al guardar");
                    info.setMessage("Sucedió un error al intentar guardar la imagen, revisar nombre del archivo, no puede contener espacios o acentos");
                    info.setTypeToast("error");
    
                    model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                    model.addAttribute("info", info);
    
                    return "views/blog/blogForm";
                }

            }else if(image.isEmpty()){
                    imageName = null;

            }

            boolean blogWasInserted = blogService.saveBlog(blog, imageName, auth.getName());

            if(blogWasInserted){
                info.setTitle("Blog registrado");
                info.setMessage("Se registro a ".concat(blog.getTitle()).concat( "correctamente"));
                info.setTypeToast("success");
                flash.addFlashAttribute("info",info);
            }
        

        return "redirect:/blog/management_list";

    }


    @GetMapping("/general")
    public String sectionGeneralBlog(@RequestParam (name="page", defaultValue = "0") int page, Model model){
        int itemsByPage = 6;
        Pageable pageRequest = PageRequest.of(page, itemsByPage);
        Page<Blog> blogs = blogService.findAllBlog(pageRequest);

        PageRender<Blog> pageRender = new PageRender<>("/blog/blog", blogs);

        model.addAttribute("listBlogs", blogs);
        model.addAttribute("page", pageRender);
        model.addAttribute("index", (itemsByPage * page));

        return "views/blog/blog";
    }

    @GetMapping("/find_details_blog/{id}")
    public String sectionGeneralBlogDetails(@PathVariable("id") Long id, Model model, RedirectAttributes flash){

        InfoToast info = new InfoToast();
        Optional<Blog> blogExists = blogService.findBlogById(id);

        if(blogExists.isPresent()){

            logger.info("Blog  "+ blogExists.toString());
            model.addAttribute("blog", blogExists.get());

            return "views/blog/detailBlog";
        }

        info.setTitle("Blog no encontrado");
        info.setMessage("La información que busca no es valida");
        info.setTypeToast("error");

        flash.addFlashAttribute("info", info);

        return "redirect:/blog/blog";

    }


    @GetMapping({"/find_update/{id}/{flag}", "/managment/details/{id}/{flag}"})
    @Secured({"ROLE_ADMINISTRADOR"})
    public String findBlogById(@PathVariable("id") Long id, @PathVariable("flag") String flag, Model model, RedirectAttributes flash){
       
       
        InfoToast info = new InfoToast();
        Optional<Blog> blogExists = blogService.findBlogById(id);

        if(blogExists.isPresent() && flag.equals("true")){

            BlogUpdateDto blogInfoDto = new BlogUpdateDto();
            BeanUtils.copyProperties(blogExists.get() , blogInfoDto);
            blogInfoDto.setPrincipal(blogExists.get().getIsPrincipal());
            model.addAttribute("blog",blogInfoDto);

            return "views/blog/blogFormUpdate";
        }else if(blogExists.isPresent() && flag.equals("false")){
            blogExists.get().getUser().setPassword(null);
            blogExists.get().getUser().setUsername(null);
            model.addAttribute("blog",blogExists.get());
             return "views/blog/blogDetailsAdmin";
        }
        

        info.setTitle("Blog no encontrado");
        info.setMessage("La información que busca no es valida");
        info.setTypeToast("error");

        flash.addFlashAttribute("info", info);

        return "redirect:/blog/management_list";
    }

    @PostMapping("/update")
    @Secured({"ROLE_ADMINISTRADOR"})
    public String updateBlog (BlogUpdateDto blogUpdateDto , Authentication auth,
                              Model model,
                              @RequestParam("imageF") MultipartFile imageF,
                              RedirectAttributes flash){

              
                               
                                logger.info("UPDATE BLOG ADMIN -> "+ blogUpdateDto.toString());
        InfoToast info = new InfoToast();

        boolean errorInsertImage = false;
        String imageName = "";

        infoMovement.setActionMovement("UPDATE");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        Optional<Blog> blogExists = blogService.findBlogById(blogUpdateDto.getId());
        Map<String, List<String>> validationsInUpdate = blogService.getValidationToUpdateBlog(blogUpdateDto);
       
       logger.info("BLOG EXIST-> AND IS PRESENT  " + blogExists.get().toString() +  "  "+ blogExists.isPresent() +"  ave  " + blogExists.isEmpty());

        if (!blogExists.isPresent()) {
            info.setTitle("Blog no valido");
            info.setMessage("La información que busca no es valida");
            info.setTypeToast("error");

            flash.addFlashAttribute("info", info);
            return "redirect:/blog/management_list";
        }

        if(blogUpdateDto.getPrincipal() && imageF.isEmpty() && (blogUpdateDto.getImage()==null)){
            logger.info("INVALIDA update"); 
            model.addAttribute("errors", validationsInUpdate);
            model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
            model.addAttribute("blog", blogUpdateDto);
            return "views/blog/blogFormUpdate";
        }

        if(!validationsInUpdate.isEmpty()){
            model.addAttribute("errors",validationsInUpdate);
            model.addAttribute("blog", blogUpdateDto);

            logger.info("DIFERENTE DE ISEMPTY (9 UPDATE)  ");
            return "views/blog/blogFormUpdate";
        }


        if(!imageF.isEmpty()){
            logger.info("IMAGE UPDATE  )  ");
            String imageNameP  = imageF.getOriginalFilename()+"";

            for (int i = 0; i < VOWELS_AND_SPACE.length; i++) {
                if(imageF.getSize()== 0 || imageF == null ){
                    break;
                }else {
                    if(imageNameP.contains(VOWELS_AND_SPACE[i])){
                        errorInsertImage = true;
                    }
                }
            }

             imageName = (errorInsertImage ? null : imageManager.insertImage(imageF));

            if(imageName == null){
                 logger.info("IMAGE UPDATE  ERROR )  ");

                info.setTitle("Error de imagen al guardar");
                info.setMessage("Sucedió un error al intentar guardar la imagen, revisar nombre del archivo, no puede contener espacios o acentos");
                info.setTypeToast("error");

                model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info", info);
                 model.addAttribute("blog", blogUpdateDto);

                return "views/blog/blogFormUpdate";
            }
            blogUpdateDto.setImage(imageName);

        }else if(imageF.isEmpty()){
                imageName = null;

        }
        

       /* if(!imageF.isEmpty()){
            logger.info("DIFERENTE DE IMAGE EMPTY 1 (9 UPDATE)  ");
            String imageName = imageManager.insertImage(imageF);

            logger.info("DIFERENTE DE IMAGE EMPTY 2 (9 UPDATE)  ");
            if(imageName == null){
                info.setTitle("Error de imagen al actualizar");
                info.setMessage("Sucedio un error al intentar guardar la imagen");
                info.setTypeToast("error");

                model.addAttribute("fileError",MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info",info);

                return "views/blog/blogFormUpdate";
            }
            blogUpdateDto.setImage(imageName);
        }*/

        boolean blogWasUpdated = blogService.updateBlog(blogUpdateDto);

        if(blogWasUpdated){
            logger.info("BLOG UPDATE ");
            info.setTitle("Blog actualizado");
            info.setMessage("Se actualizó el blog ".concat(blogUpdateDto.getTitle()).concat(" correctamente"));
            info.setTypeToast("success");
            flash.addFlashAttribute("info", info);
        }

        return "redirect:/blog/management_list";
    }



}