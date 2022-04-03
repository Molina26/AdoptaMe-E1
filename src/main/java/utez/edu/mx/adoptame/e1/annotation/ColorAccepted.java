package utez.edu.mx.adoptame.e1.annotation;

import utez.edu.mx.adoptame.e1.validation.ColorAcceptedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ColorAcceptedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColorAccepted {

    String message() default "{adoptame.constraints.color.accepted.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
