package utez.edu.mx.adoptame.e1.validation;

import utez.edu.mx.adoptame.e1.annotation.ValueOfEnumAccepted;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumAcceptedValidator implements ConstraintValidator<ValueOfEnumAccepted, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnumAccepted annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        return acceptedValues.contains(value);
    }
}
