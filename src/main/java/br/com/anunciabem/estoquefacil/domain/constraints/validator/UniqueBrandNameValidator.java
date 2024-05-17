package br.com.anunciabem.estoquefacil.domain.constraints.validator;

import br.com.anunciabem.estoquefacil.annotations.Validator;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueBrandName;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Validator
@AllArgsConstructor
public class UniqueBrandNameValidator implements ConstraintValidator<UniqueBrandName, String> {

  private final BrandRepository brandRepository;

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return value != null && !this.brandRepository.existsByName(value);
  }

}
