package br.com.anunciabem.estoquefacil.domain.constraints;

import br.com.anunciabem.estoquefacil.domain.constraints.validator.UniqueModelNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueModelNameValidator.class)
public @interface UniqueModelName {

  String message() default "Model already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
