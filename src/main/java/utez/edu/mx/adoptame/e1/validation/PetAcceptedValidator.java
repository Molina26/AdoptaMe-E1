package utez.edu.mx.adoptame.e1.validation;

import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.adoptame.e1.annotation.PetAccepted;
import utez.edu.mx.adoptame.e1.entity.Pet;
import utez.edu.mx.adoptame.e1.service.PetServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PetAcceptedValidator implements ConstraintValidator<PetAccepted, Long> {

    @Autowired
    private PetServiceImpl petService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        boolean flag = false;

        if ( value != 0) {

            Optional<Pet> pet = petService.findPetById(value);

            if (pet.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
