package io.github.vendas.validation;

import io.github.vendas.validation.constraintValidator.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptySet {

    String message() default "Lista de itens não pode estar vazia!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
