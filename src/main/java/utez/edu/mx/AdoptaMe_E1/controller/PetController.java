package utez.edu.mx.AdoptaMe_E1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.AdoptaMe_E1.entity.Color;
import utez.edu.mx.AdoptaMe_E1.entity.Pet;
import utez.edu.mx.AdoptaMe_E1.entity.Personality;
import utez.edu.mx.AdoptaMe_E1.entity.Size;
import utez.edu.mx.AdoptaMe_E1.model.responses.InfoToast;
import utez.edu.mx.AdoptaMe_E1.service.ColorServiceImpl;
import utez.edu.mx.AdoptaMe_E1.service.PersonalityServiceImpl;
import utez.edu.mx.AdoptaMe_E1.service.PetServiceImpl;
import utez.edu.mx.AdoptaMe_E1.service.SizeServiceImpl;
import utez.edu.mx.AdoptaMe_E1.util.ImageManager;
import utez.edu.mx.AdoptaMe_E1.util.PageRender;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    private final PetServiceImpl petService;

    private final ColorServiceImpl colorService;

    private final PersonalityServiceImpl personalityService;

    private final SizeServiceImpl sizeService;

    private final ImageManager imageManager;

    private final Logger logger = LoggerFactory.getLogger(PetController.class);

    private List<Color> listColors = new ArrayList<>();

    private List<Personality> listPersonalities = new ArrayList<>();

    private List<Size> listSizes = new ArrayList<>();


    public PetController(PetServiceImpl petService, ColorServiceImpl colorService, PersonalityServiceImpl personalityService, SizeServiceImpl sizeService, ImageManager imageManager) {
        this.petService = petService;
        this.colorService = colorService;
        this.personalityService = personalityService;
        this.sizeService = sizeService;
        this.imageManager = imageManager;

        this.definePetsInfoLists();
    }


    @GetMapping("/management_list")
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_VOLUNTARIO"})
    public String findListPetsManagement(@RequestParam(name = "page", defaultValue = "0")int page, Model model) {
        int itemsByPage = 4;
        Pageable pageRequest = PageRequest.of(page, itemsByPage);

        Page<Pet> pets = petService.findAllPageable(pageRequest);

        PageRender<Pet> pageRender = new PageRender<>("/pets/management_list", pets);

        model.addAttribute("listPets", pets);
        model.addAttribute("page", pageRender);

        return "views/pet/listTablePets";
    }

    @GetMapping("/create")
    @Secured({"ROLE_VOLUNTARIO"})
    public String createPet(Pet pet, Model model) {
        this.definePetsInfoLists();
        model.addAttribute("listSizes", this.listSizes);
        model.addAttribute("listPersonalities", this.listPersonalities);
        model.addAttribute("listColors", this.listColors);
        return "views/pet/addPet";
    }

    @PostMapping("/save")
    @Secured({"ROLE_VOLUNTARIO"})
    public String save(@Valid Pet pet, BindingResult result, Model model, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes flash) {
        InfoToast info = new InfoToast();

        logger.info(pet.toString());

        if (result.hasErrors() || imageFile.isEmpty()) {
            model.addAttribute("listSizes", this.listSizes);
            model.addAttribute("listPersonalities", this.listPersonalities);
            model.addAttribute("listColors", this.listColors);
            model.addAttribute("fileError", "Debe de seleccionar una imagen");
            return "views/pet/addPet";
        }

        if (!imageFile.isEmpty()) {
            String imageName = imageManager.insertImage(imageFile);

            if (imageName == null) {

                info.setTitle("Error en el servidor");
                info.setMessage("Sucedio un error al intentar guardar la imagen");
                info.setTypeToast("error");

                model.addAttribute("listSizes", this.listSizes);
                model.addAttribute("listPersonalities", this.listPersonalities);
                model.addAttribute("listColors", this.listColors);
                model.addAttribute("fileError", "Debe de seleccionar una imagen");
                model.addAttribute("info", info);
                return "views/pet/addPet";
            }

            pet.setImage(imageName);

        }

        boolean petWasInserted = petService.create(pet);

        if (petWasInserted) {
            info.setTitle("Mascota registrada");
            info.setMessage("Se registro a ".concat(pet.getName()).concat(" correctamente"));
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
