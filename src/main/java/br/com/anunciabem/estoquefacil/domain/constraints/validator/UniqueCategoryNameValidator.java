package br.com.anunciabem.estoquefacil.domain.constraints.validator;

import br.com.anunciabem.estoquefacil.annotations.Validator;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueCategoryName;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Validator
@AllArgsConstructor
public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {

  private final CategoryRepository categoryRepository;

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return value != null && !this.categoryRepository.existsByName(value);
  }

}
