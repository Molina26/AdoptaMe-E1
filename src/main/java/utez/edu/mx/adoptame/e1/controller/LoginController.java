package utez.edu.mx.adoptame.e1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.request.user.UserInsertDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.UserAdoptameServiceImpl;


import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {



    private final UserAdoptameServiceImpl userAdoptameService;

    public LoginController(
            UserAdoptameServiceImpl userAdoptameService) {
      
        this.userAdoptameService = userAdoptameService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
            RedirectAttributes flash) {
        InfoToast info = new InfoToast();
        model.addAttribute("login", true);
        model.addAttribute("user", new UserInsertDto());
        if (principal != null) {
            info.setTitle("Sesión iniciada");
            info.setMessage("Ya cuenta con una sesión activa");
            info.setTypeToast("info");
            flash.addFlashAttribute("info", info);
            return "redirect:/";
        }

        if (error != null) {
            info.setTitle("Error de acceso");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            model.addAttribute("info", info);

            return "views/auth/login";
        }

        if (logout != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new UserInsertDto());
     

        return "views/auth/login";
    }

    @PostMapping("/create-acount")
    public String createAcount(Model model, UserInsertDto userDto, RedirectAttributes flash) {
        model.addAttribute("login", true);

        String phone = userDto.getPhone().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "")
                .replaceAll("-", "");

        userDto.setPhone(phone);
        InfoToast info = new InfoToast();

        Map<String, List<String>> validationsAcount = userAdoptameService.getValidationInsertUserAdoptame(userDto);

        String passwordFine = userDto.getPassword().substring(0,userDto.getPassword().indexOf(","));
        userDto.setPassword(passwordFine);

        if (!validationsAcount.isEmpty()) {
            info.setTitle("Error al crear la cuenta");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("errors", validationsAcount);
            model.addAttribute("user", userDto);
            return "views/auth/login";
        }

        
        UserAdoptame userExist = userAdoptameService.findUserByUsername(userDto.getUsername());
        if (userExist != null) {
            info.setTitle("Error al crear la cuenta");
            info.setMessage("El username ya existe");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("user", userDto);
            return "views/auth/login";
        }
        boolean userWasInsert = userAdoptameService.saveUser(userDto);

        if (userWasInsert) {

            info.setTitle("Cuenta Creada");
            info.setMessage("Bienvenido ".concat(userDto.getUsername()));
            info.setTypeToast("success");
            model.addAttribute("info", info);
            UserInsertDto newUserDto = new UserInsertDto();
            model.addAttribute("user", newUserDto);
            return "views/auth/login";
        } else if(!userWasInsert){
            info.setTitle("Error al crear la cuenta");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("user", userDto);
            return "views/auth/login";
        }

        return "views/auth/login";
       
    }

}
