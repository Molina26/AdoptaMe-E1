package utez.edu.mx.AdoptaMe_E1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.AdoptaMe_E1.model.responses.InfoToast;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
                        RedirectAttributes flash) {
        InfoToast info = new InfoToast();
        model.addAttribute("login", true);

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

        return "views/auth/login";
    }

}
