package utez.edu.mx.AdoptaMe_E1.validation;

import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.AdoptaMe_E1.annotation.ColorAccepted;
import utez.edu.mx.AdoptaMe_E1.entity.Color;
import utez.edu.mx.AdoptaMe_E1.service.ColorServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ColorAcceptedValidator implements ConstraintValidator<ColorAccepted, Color> {

    @Autowired
    private ColorServiceImpl colorService;

    @Override
    public boolean isValid(Color value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Color> item = colorService.findColorById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}