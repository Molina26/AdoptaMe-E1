package utez.edu.mx.adoptame.e1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.PetServiceImpl;
import utez.edu.mx.adoptame.e1.util.GeneralInfoApp;
import utez.edu.mx.adoptame.e1.util.ValidationCredentials;

import java.util.Optional;

@Controller
@RequestMapping("/adoptions")
public class AdoptionApplicationController {

  private final GeneralInfoApp generalInfoApp;

  private final PetServiceImpl petService;

  public AdoptionApplicationController(PetServiceImpl petService, GeneralInfoApp generalInfoApp){
    this.petService = petService;
    this.generalInfoApp = generalInfoApp;
  }

  @GetMapping("/list")
  @Secured({"ROLE_VOLUNTARIO", "ROLE_ADOPTADOR"})
  public String listAdoptionApplications(Authentication auth,Model model) {

    boolean isVoluntario = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_VOLUNTARIO");

    if (isVoluntario) {
      System.err.println("Es voluntario");
    }

    return "/views/adoption/listAdoptions";
  }

  @GetMapping("/detail/{id}")
  public String findPetDetail(@PathVariable("id") Long id,
                              Authentication auth,
                              Model model,
                              RedirectAttributes flash) {

    String typePet = generalInfoApp.getTypePet();

    InfoToast info = new InfoToast();

    Optional<Pet> petExisted = petService.findPetById(id);

    if (petExisted.isPresent()) {

      model.addAttribute("pet", petExisted.get());

      if (auth != null) {

        boolean isAdoptador = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_ADOPTADOR");

        if (isAdoptador) {
          AdoptionRegisterDto adoptionRegisterDto = new AdoptionRegisterDto();

          adoptionRegisterDto.setUsername(auth.getName());
          adoptionRegisterDto.setPetId(petExisted.get().getId());
          model.addAttribute("adoptionInfo", adoptionRegisterDto);
        }
      }

      return "views/adoption/detailAdoption";
    }

    info.setTitle("Mascota no encontrada");
    info.setMessage("La información que busca no es correcta");
    info.setTypeToast("error");

    flash.addFlashAttribute("info", info);

    return "redirect:/pets/adopt/".concat(typePet);
  }

  @PostMapping("/save")
  @Secured({"ROLE_ADOPTADOR"})
  public String saveAdoptionApplication(AdoptionRegisterDto adoption) {

    // validate to the user exist in the database

    // do you send more to one adoption petition?

    return "";
  }
}
