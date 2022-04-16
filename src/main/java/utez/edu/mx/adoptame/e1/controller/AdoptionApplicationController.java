package utez.edu.mx.adoptame.e1.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.adoptame.e1.entity.AdoptionApplication;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.enums.StateAdoptionApplication;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionRegisterDto;
import utez.edu.mx.adoptame.e1.model.request.adoption.AdoptionUpdateDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.service.AdoptionApplicationServiceImpl;
import utez.edu.mx.adoptame.e1.service.PetServiceImpl;
import utez.edu.mx.adoptame.e1.util.GeneralInfoApp;
import utez.edu.mx.adoptame.e1.util.InfoMovement;
import utez.edu.mx.adoptame.e1.util.PageRender;
import utez.edu.mx.adoptame.e1.util.ValidationCredentials;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/adoptions")
public class AdoptionApplicationController {

  private final GeneralInfoApp generalInfoApp;

  private final PetServiceImpl petService;

  private final AdoptionApplicationServiceImpl adoptionApplicationService;

  private final InfoMovement infoMovement;

  private final String MODULE_NAME = "ADOPTION_APPLICATION";

  public AdoptionApplicationController(PetServiceImpl petService,
                                       AdoptionApplicationServiceImpl adoptionApplicationService,
                                       GeneralInfoApp generalInfoApp,
                                       InfoMovement infoMovement){
    this.petService = petService;
    this.generalInfoApp = generalInfoApp;
    this.adoptionApplicationService = adoptionApplicationService;
    this.infoMovement = infoMovement;
  }

  @GetMapping("/list")
  @Secured({"ROLE_VOLUNTARIO", "ROLE_ADOPTADOR"})
  public String listAdoptionApplications(@RequestParam(name = "page", defaultValue = "0") int page,
                                         Authentication auth,
                                         Model model) {

    boolean isVoluntario = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_VOLUNTARIO");

    int itemsByPage = 6;
    int index = itemsByPage * page;

    Pageable pageRequest = PageRequest.of(page, itemsByPage, Sort.by("applicationDate").descending());

    if (isVoluntario) {
      Page<AdoptionApplication> adoptionsToVoluntary = adoptionApplicationService.findAllAdoptionApplications(pageRequest);

      PageRender<AdoptionApplication> pageRenderToVoluntary = new PageRender<>("/adoptions/list", adoptionsToVoluntary);

      model.addAttribute("adoptionsList", adoptionsToVoluntary);
      model.addAttribute("page", pageRenderToVoluntary);

    } else {
      Page<AdoptionApplication> adoptionsToAdopter = adoptionApplicationService.findAdoptionApplicationsByUsername(auth.getName(), pageRequest);

      PageRender<AdoptionApplication> pageRenderToAdopter = new PageRender<>("/adoptions/list", adoptionsToAdopter);

      model.addAttribute("adoptionsList", adoptionsToAdopter);
      model.addAttribute("page", pageRenderToAdopter);

    }

    model.addAttribute("index", index);

    return "/views/adoption/listAdoptions";
  }

  @GetMapping("/detail/{id}")
  public String findPetDetailToAdopt(@PathVariable("id") Long id,
                              Authentication auth,
                              Model model,
                              RedirectAttributes flash) {

    String typePet = generalInfoApp.getTypePet();

    InfoToast info = new InfoToast();

    Optional<Pet> petExisted = petService.findPetById(id);

    if (petExisted.isPresent()) {

      if (petExisted.get().getAvailableAdoption().equals(true)) {
        model.addAttribute("pet", petExisted.get());

        if (auth != null) {
          boolean isAdoptador = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_ADOPTADOR");

          if (isAdoptador) {
            // will define the information of this adopter will like adopt a new pet
            AdoptionRegisterDto adoptionRegisterDto = new AdoptionRegisterDto();

            adoptionRegisterDto.setUsername(auth.getName());
            adoptionRegisterDto.setPetId(petExisted.get().getId());
            model.addAttribute("adoptionInfo", adoptionRegisterDto);
          }
        }

        return "views/adoption/detailAdoption";
      }


    }

    info.setTitle("Mascota no encontrada");
    info.setMessage("La información que busca no es correcta");
    info.setTypeToast("error");

    flash.addFlashAttribute("info", info);

    return "redirect:/pets/adopt/".concat(typePet);
  }

  @PostMapping("/save")
  @Secured({"ROLE_ADOPTADOR"})
  public String saveAdoptionApplication(AdoptionRegisterDto adoption,
                                        Authentication auth,
                                        Model model,
                                        RedirectAttributes flash) {

    Map<String, List<String>> validation = adoptionApplicationService.getValidationToCreateAdoptionAplication(adoption);

    infoMovement.setActionMovement("INSERT");
    infoMovement.setUsername(auth.getName());
    infoMovement.setModuleName(MODULE_NAME);

    if (!validation.isEmpty()) {
      model.addAttribute("errors", validation);
      return "views/adoption/detailAdoption";
    }

    boolean adoptionHadInserted = adoptionApplicationService.createApplication(adoption);

    if (adoptionHadInserted) {
      InfoToast infoToast = new InfoToast();
      infoToast.setTypeToast("success");
      infoToast.setTitle("Se ha registrado tu solicitud");
      infoToast.setMessage("Tu solicitud se ha enviado correctamente, espera la respuesta por parte de un voluntario");
      flash.addFlashAttribute("info", infoToast);
    }

    return "redirect:/adoptions/list";
  }

  @GetMapping("/report/{id}")
  @Secured({"ROLE_VOLUNTARIO", "ROLE_ADOPTADOR"})
  public String findPetToReport(@PathVariable("id") Long id,
                                     Authentication auth,
                                     Model model,
                                     RedirectAttributes flash) {

    Optional<AdoptionApplication> applicationExisted = adoptionApplicationService.findAdoptionApplicationId(id);

    boolean isVoluntario = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_VOLUNTARIO");

    InfoToast info = new InfoToast();

    if (applicationExisted.isPresent()) {
      model.addAttribute("applicationInfo", applicationExisted.get());
      Long petId = applicationExisted.get().getPet().getId();

      if (isVoluntario) {
        Optional<Pet> petExisted = petService.findPetById(petId);

        if (petExisted.isPresent()) {

          model.addAttribute("pet", petExisted.get());

          if (applicationExisted.get().getClosedDate() == null) {
            AdoptionUpdateDto adoptionUpdateDto = new AdoptionUpdateDto();
            BeanUtils.copyProperties(applicationExisted.get(), adoptionUpdateDto);

            model.addAttribute("adoptionForm", adoptionUpdateDto);
            model.addAttribute("listStatesAdoption", StateAdoptionApplication.values());
          }

          return "views/adoption/reportAdoption";
        }
      } else {
        if (applicationExisted.get().getUser().getUsername().equals(auth.getName())) {
          Optional<Pet> petExisted = petService.findPetById(petId);

          if (petExisted.isPresent()) {

            model.addAttribute("pet", petExisted.get());

            return "views/adoption/reportAdoption";
          }
        }

        info.setTitle("No tienes acceso");
        info.setMessage("La información que busca no es correspondiente a su cuenta de usuario");
        info.setTypeToast("error");
      }
    } else {
      info.setTitle("Información no encontrada");
      info.setMessage("La información que busca no es correcta");
      info.setTypeToast("error");
    }

    flash.addFlashAttribute("info", info);

    return "redirect:/adoptions/list";
  }

  @PostMapping("/response_application")
  @Secured({"ROLE_VOLUNTARIO"})
  public String responseApplicationAdoptionPet(AdoptionUpdateDto adoptionUpdateDto,
                                               Authentication auth,
                                               Model model,
                                               RedirectAttributes flash) {

    infoMovement.setActionMovement("INSERT");
    infoMovement.setUsername(auth.getName());
    infoMovement.setModuleName(MODULE_NAME);

    Optional<AdoptionApplication> applicationExisted = adoptionApplicationService.findAdoptionApplicationId(adoptionUpdateDto.getId());

    if (applicationExisted.isPresent()) {
      Map<String, List<String>> validation = adoptionApplicationService.getValidationToChangeStateAdoptionAplication(adoptionUpdateDto);


      if (!validation.isEmpty() || adoptionUpdateDto.getState().equals("pendiente")) {
        model.addAttribute("errors", validation);

        Optional<Pet> petExisted = petService.findPetById(applicationExisted.get().getPet().getId());

        petExisted.ifPresent(pet -> model.addAttribute("pet", pet));

        model.addAttribute("adoptionForm", adoptionUpdateDto);
        model.addAttribute("listStatesAdoption", StateAdoptionApplication.values());
        model.addAttribute("applicationInfo", applicationExisted.get());


        if (adoptionUpdateDto.getState().equals("pendiente")) {
          model.addAttribute("errorMessage", "Debe de indicar un valor diferente de pendiente");
        }
        return "views/adoption/reportAdoption";
      }

      boolean flag = adoptionApplicationService.changeStateAdoption(adoptionUpdateDto);

      if (flag) {
        InfoToast info = new InfoToast();
        info.setTypeToast("success");
        info.setMessage("Ha respondido ha la solicitud");
        info.setTitle("Solicitud de adopción resuelta");
        flash.addFlashAttribute("info", info);
      }
    }

    return "redirect:/adoptions/list";
  }

}
