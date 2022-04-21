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
import utez.edu.mx.adoptame.e1.util.InfoMovement;

import java.util.Map;
import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping(path = "/user")
public class UserAdoptameController {
    private final InfoMovement infoMovement;
    private final UserAdoptameServiceImpl userAdoptameService;
    private final String MODULE_NAME = "USER";

    UserAdoptameController (UserAdoptameServiceImpl userAdoptameService, InfoMovement infoMovement){
        this.userAdoptameService = userAdoptameService;
        this.infoMovement = infoMovement;
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
            info.setTitle("Usuario no encontrado");
            info.setMessage("La información que busca no es correcta");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("user", userDto);
        }

        return "views/user/profile";
    }


    @PostMapping("/update")
    @Secured({"ROLE_ADOPTADOR" })
    public String updateUser(Model model, Authentication auth, UserUpdateDto userUpdateDto) {
        
        infoMovement.setActionMovement("UPDATE");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        InfoToast info = new InfoToast();
        userUpdateDto.setUsername(auth.getName());
        Map<String, List<String>> validation = userAdoptameService.getValidationToUpdateUser(userUpdateDto);

        if(!validation.isEmpty()){
            model.addAttribute("errors", validation);
            model.addAttribute("user", userUpdateDto);
            return "views/user/profile";
        }
       
        boolean flagUpdateUser = userAdoptameService.updateUser(userUpdateDto);
        UserAdoptame user = userAdoptameService.findUserByUsername(auth.getName());
        Optional<DetailUserinfo> userInfo = userAdoptameService.findDetailsUserInfo(user);

        if(flagUpdateUser && userInfo.isPresent()){
            BeanUtils.copyProperties(userInfo.get() , userUpdateDto );

            userUpdateDto.setUsername(user.getUsername());
            userUpdateDto.setId(user.getId());
            userUpdateDto.setFirstLastname(user.getFirstLastname());
            userUpdateDto.setSecondLastname(user.getSecondLastname());
            userUpdateDto.setName(user.getName());
            model.addAttribute("user", userUpdateDto);


            info.setTitle("Usuario actualizado");
            info.setMessage("Se actualizó el usuario ".concat(auth.getName()).concat( "correctamente"));
            info.setTypeToast("success");
            model.addAttribute("info",info);
        }
      

        return "views/user/profile";
    }






}
