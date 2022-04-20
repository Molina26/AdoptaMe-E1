package utez.edu.mx.adoptame.e1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.*;

@Controller
@RequestMapping(path = "/user")
public class UserAdoptameController {
    private final UserAdoptameServiceImpl userAdoptameService;

    UserAdoptameController (UserAdoptameServiceImpl userAdoptameService){
        this.userAdoptameService = userAdoptameService;
    }


    @GetMapping("/details")
    @Secured({"ROLE_ADOPTADOR" })
    public String findUserByUsername(Model model, Authentication auth) {

        InfoToast info = new InfoToast();


        UserAdoptame user = userAdoptameService.findUserByUsername(auth.getName());
        model.addAttribute("user", user);

        return "/views/user/profile";
    }


    @GetMapping("/update")
    @Secured({"ROLE_ADOPTADOR" })
    public String updateUser(Model model, Authentication auth) {


        return "/views/user/profile";
    }






}
