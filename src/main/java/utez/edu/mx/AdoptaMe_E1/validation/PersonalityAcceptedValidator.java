package utez.edu.mx.AdoptaMe_E1.validation;

import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.AdoptaMe_E1.annotation.PersonalityAccepted;
import utez.edu.mx.AdoptaMe_E1.entity.Personality;
import utez.edu.mx.AdoptaMe_E1.service.PersonalityServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PersonalityAcceptedValidator  implements ConstraintValidator<PersonalityAccepted, Personality> {

    @Autowired
    private PersonalityServiceImpl personalityService;

    @Override
    public boolean isValid(Personality value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Personality> item = personalityService.findPersonalityById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
