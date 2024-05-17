package br.com.anunciabem.estoquefacil.domain.constraints;

import br.com.anunciabem.estoquefacil.domain.constraints.validator.NullableValueStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NullableValueStringValidator.class)
public @interface NullableValue {

  String message() default "must be null or not empty";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
