package br.com.anunciabem.estoquefacil.domain.constraints.validator;

import br.com.anunciabem.estoquefacil.annotations.Validator;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueModelName;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Validator
@AllArgsConstructor
public class UniqueModelNameValidator implements ConstraintValidator<UniqueModelName, String> {

  private final ModelRepository modelRepository;

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return value != null && !this.modelRepository.existsByName(value);
  }

}
