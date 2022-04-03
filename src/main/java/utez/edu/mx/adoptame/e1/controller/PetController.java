package utez.edu.mx.adoptame.e1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.adoptame.e1.entity.Color;
import utez.edu.mx.adoptame.e1.entity.Personality;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.entity.Size;
import utez.edu.mx.adoptame.e1.model.request.pet.PetInsertDto;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;

import utez.edu.mx.adoptame.e1.service.PersonalityServiceImpl;
import utez.edu.mx.adoptame.e1.service.PetServiceImpl;
import utez.edu.mx.adoptame.e1.service.SizeServiceImpl;
import utez.edu.mx.adoptame.e1.util.ImageManager;
import utez.edu.mx.adoptame.e1.util.InfoMovement;
import utez.edu.mx.adoptame.e1.util.PageRender;
import utez.edu.mx.adoptame.e1.service.ColorServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    private final PetServiceImpl petService;

    private final ColorServiceImpl colorService;

    private final PersonalityServiceImpl personalityService;

    private final SizeServiceImpl sizeService;

    private final ImageManager imageManager;

    private final InfoMovement infoMovement;

    private final Logger logger = LoggerFactory.getLogger(PetController.class);

    private List<Color> listColors = new ArrayList<>();

    private List<Personality> listPersonalities = new ArrayList<>();

    private List<Size> listSizes = new ArrayList<>();

    private final String LIST_COLORS_NAME = "listColors";

    private final String LIST_PERSONALITIES_NAME = "listPersonalities";

    private final String LIST_SIZES_NAME = "listSizes";

    private final String MODULE_NAME = "PETS";

    private final String MESSAGE_FILE_NOT_SELECTED = "Debe de seleccionar una imagen";


    public PetController(PetServiceImpl petService,
                         ColorServiceImpl colorService,
                         PersonalityServiceImpl personalityService,
                         SizeServiceImpl sizeService,
                         ImageManager imageManager,
                         InfoMovement infoMovement) {
        this.petService = petService;
        this.colorService = colorService;
        this.personalityService = personalityService;
        this.sizeService = sizeService;
        this.imageManager = imageManager;
        this.infoMovement = infoMovement;
        this.definePetsInfoLists();
    }


    @GetMapping("/management_list")
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_VOLUNTARIO"})
    public String findListPetsManagement(@RequestParam(name = "page", defaultValue = "0")int page, Model model) {
        int itemsByPage = 5;
        Pageable pageRequest = PageRequest.of(page, itemsByPage);

        Page<Pet> pets = petService.findAll(pageRequest);

        PageRender<Pet> pageRender = new PageRender<>("/pets/management_list", pets);

        model.addAttribute("listPets", pets);
        model.addAttribute("page", pageRender);
        model.addAttribute("index", (itemsByPage * page));

        return "views/pet/listTablePets";
    }

    @GetMapping("/create")
    @Secured({"ROLE_VOLUNTARIO"})
    public String createPet(Model model) {
        this.definePetsInfoLists();
        model.addAttribute("pet", new PetInsertDto());
        model.addAttribute(LIST_COLORS_NAME, this.listColors);
        model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
        model.addAttribute(LIST_SIZES_NAME, this.listSizes);

        return "views/pet/addPet";
    }

    @PostMapping("/save")
    @Secured({"ROLE_VOLUNTARIO"})
    public String save(@ModelAttribute("pet") PetInsertDto pet,
                       Authentication auth ,
                       Model model,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes flash) throws BindException {
        InfoToast info = new InfoToast();

        Map<String, List<String>> validation = petService.getValidationToInsert(pet);

        infoMovement.setActionMovement("INSERT");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        if (!validation.isEmpty() || imageFile.isEmpty()) {
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);
            model.addAttribute("errors", validation);
            model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
            return "views/pet/addPet";
        }

        if (!imageFile.isEmpty()) {
            String imageName = imageManager.insertImage(imageFile);

            if (imageName == null) {

                info.setTitle("Error en el servidor");
                info.setMessage("Sucedio un error al intentar guardar la imagen");
                info.setTypeToast("error");

                model.addAttribute(LIST_COLORS_NAME, this.listColors);
                model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
                model.addAttribute(LIST_SIZES_NAME, this.listSizes);

                model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info", info);
                return "views/pet/addPet";
            }

            boolean petWasInserted = petService.create(pet, imageName);

            if (petWasInserted) {
                info.setTitle("Mascota registrada");
                info.setMessage("Se registro a ".concat(pet.getName()).concat(" correctamente"));
                info.setTypeToast("success");

                flash.addFlashAttribute("info", info);
            }
        }

        return "redirect:/pets/management_list";
    }

    @GetMapping("/find_update/{id}")
    @Secured({"ROLE_VOLUNTARIO"})
    public String findPetToUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        Optional<Pet> petExisted = petService.findPetById(id);

        if (petExisted.isPresent()) {
            this.definePetsInfoLists();
            model.addAttribute("pet", petExisted.get());
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);

            return "views/pet/updatePet";

        }

        return "redirect:/pets/management_list";
    }

    @PostMapping("/update")
    @Secured({"ROLE_VOLUNTARIO"})
    public String update(@Valid Pet pet, BindingResult result, Authentication auth, Model model, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes flash) {
        InfoToast info = new InfoToast();

        infoMovement.setActionMovement("UPDATE");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        Optional<Pet> petExisted = petService.findPetById(pet.getId());

        if (petExisted.isEmpty()) {
            info.setTitle("Mascota no valida");
            info.setMessage("La información que busca no es valida");
            info.setTypeToast("error");

            flash.addFlashAttribute("info", info);
            return "redirect:/pets/management_list";
        }

        Pet petPersistent = petExisted.get();
        pet.setImage(petPersistent.getImage());
        pet.setCreatedAt(petPersistent.getCreatedAt());
        pet.setAvailableAdoption(petPersistent.getAvailableAdoption());

        if (result.hasErrors()) {
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);
            return "views/pet/updatePet";
        }

        if (!imageFile.isEmpty()) {
            String imageName = imageManager.insertImage(imageFile);

            if (imageName == null) {

                info.setTitle("Error en el servidor");
                info.setMessage("Sucedio un error al intentar guardar la imagen");
                info.setTypeToast("error");

                model.addAttribute(LIST_COLORS_NAME, this.listColors);
                model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
                model.addAttribute(LIST_SIZES_NAME, this.listSizes);
                model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info", info);
                return "views/pet/updatePet";
            }

            pet.setImage(imageName);

        }

        boolean petWasUpdated = petService.update(pet);

        if (petWasUpdated) {
            info.setTitle("Mascota actualizada");
            info.setMessage("Se actualizo a ".concat(pet.getName()).concat(" correctamente"));
            info.setTypeToast("success");

            flash.addFlashAttribute("info", info);
        }

        return "redirect:/pets/management_list";
    }


    private void definePetsInfoLists() {
        Long itemsColors = colorService.countAllColors();
        int listColorsSize = this.listColors.size();

        Long itemsPersonalities = personalityService.countAllPersonalities();
        int listPersonalitiesSize = this.listPersonalities.size();

        Long itemsSizes = sizeService.countAllSizes();
        int listSizesSize = this.listSizes.size();

        if (listColorsSize == 0 || itemsColors > listColorsSize || itemsColors < listColorsSize) {
            this.listColors = colorService.findAllColors();
        }

        if (listPersonalitiesSize == 0 || itemsPersonalities > listPersonalitiesSize || itemsPersonalities < listPersonalitiesSize) {
            this.listPersonalities = personalityService.findAllPersonalities();
        }

        if (listSizesSize == 0 || itemsSizes > listSizesSize || itemsSizes < listSizesSize) {
            this.listSizes = sizeService.findAllSizes();
        }
    }
}
