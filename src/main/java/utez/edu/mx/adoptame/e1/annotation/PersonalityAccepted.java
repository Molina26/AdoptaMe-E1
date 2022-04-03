package utez.edu.mx.adoptame.e1.annotation;

import utez.edu.mx.adoptame.e1.validation.PersonalityAcceptedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PersonalityAcceptedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalityAccepted {
    String message() default "{adoptame.constraints.personality.accepted.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
