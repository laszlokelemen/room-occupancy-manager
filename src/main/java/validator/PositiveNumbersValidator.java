package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.List;

public class PositiveNumbersValidator implements ConstraintValidator<PositiveNumbers, List<BigDecimal>> {

    @Override
    public void initialize(PositiveNumbers constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates a list of numbers.
     *
     * @param  numbers   the list of numbers to validate
     * @param  context   the validation context
     * @return           true if the list is valid, false otherwise
     */
    @Override
    public boolean isValid(List<BigDecimal> numbers, ConstraintValidatorContext context) {
        if (numbers == null) {
            return true;
        }

        for (BigDecimal number : numbers) {
            if (number == null || number.compareTo(BigDecimal.ZERO) < 0) {
                return false;
            }
        }

        return true;
    }
}

