package utez.edu.mx.adoptame.e1.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import utez.edu.mx.adoptame.e1.entity.DetailUserinfo;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.request.user.UserUpdateDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.*;

import java.util.Optional;

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

        UserAdoptame user = userAdoptameService.findUserByUsername(auth.getName());
        Optional<DetailUserinfo> userInfo = userAdoptameService.findDetailsUserInfo(user);
        InfoToast info = new InfoToast();
        UserUpdateDto userDto = new UserUpdateDto();

        if(userInfo.isPresent()){

            BeanUtils.copyProperties(userInfo.get() , userDto );

            userDto.setUsername(user.getUsername());
            userDto.setId(user.getId());
            userDto.setFirstLastname(user.getFirstLastname());
            userDto.setSecondLastname(user.getSecondLastname());
            userDto.setName(user.getName());
            model.addAttribute("user", userDto);
        }else{
            info.setTitle("Mascota no encontrada");
            info.setMessage("La información que busca no es correcta");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("user", userDto);
        }

        return "views/user/profile";
    }


    @GetMapping("/update")
    @Secured({"ROLE_ADOPTADOR" })
    public String updateUser(Model model, Authentication auth) {


        return "/views/user/profile";
    }






}
