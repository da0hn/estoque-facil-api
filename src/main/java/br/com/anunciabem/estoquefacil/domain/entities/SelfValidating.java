package br.com.anunciabem.estoquefacil.domain.entities;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.data.annotation.Transient;

public abstract class SelfValidating {

  @Transient
  private final Validator validator;

  protected SelfValidating() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @SuppressWarnings("unchecked")
  protected void validateSelf() {
    final var violations = this.validator.validate(this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

}
