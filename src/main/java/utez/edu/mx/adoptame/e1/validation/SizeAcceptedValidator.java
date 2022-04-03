package utez.edu.mx.adoptame.e1.validation;

import org.springframework.beans.factory.annotation.Autowired;
import utez.edu.mx.adoptame.e1.annotation.SizeAccepted;
import utez.edu.mx.adoptame.e1.entity.Size;
import utez.edu.mx.adoptame.e1.service.SizeServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SizeAcceptedValidator implements ConstraintValidator<SizeAccepted, Size> {

    @Autowired
    private SizeServiceImpl sizeService;

    @Override
    public boolean isValid(Size value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Size> item = sizeService.findSizeById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
