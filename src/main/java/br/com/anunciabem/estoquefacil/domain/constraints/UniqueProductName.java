package br.com.anunciabem.estoquefacil.domain.constraints;

import br.com.anunciabem.estoquefacil.domain.constraints.validator.UniqueProductNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueProductNameValidator.class)
public @interface UniqueProductName {

  String message() default "Product already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
