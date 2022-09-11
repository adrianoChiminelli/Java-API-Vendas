package io.github.vendas.validation.constraintValidator;

import io.github.vendas.validation.NotEmptySet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptySet, Set> {

    @Override
    public void initialize(NotEmptySet constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set set, ConstraintValidatorContext constraintValidatorContext) {
        return set != null && !set.isEmpty();
    }
}
