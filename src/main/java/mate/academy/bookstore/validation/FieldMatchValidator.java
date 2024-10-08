package mate.academy.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String first;
    private String second;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Object firstValue = new BeanWrapperImpl(object).getPropertyValue(first);
        Object secondValue = new BeanWrapperImpl(object).getPropertyValue(second);
        if (firstValue != null) {
            return firstValue.equals(secondValue);
        }
        return secondValue == null;
    }
}
