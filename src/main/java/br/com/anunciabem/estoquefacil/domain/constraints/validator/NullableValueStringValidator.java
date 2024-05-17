package br.com.anunciabem.estoquefacil.domain.constraints.validator;

import br.com.anunciabem.estoquefacil.annotations.Validator;
import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueUsername;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

@Validator
@AllArgsConstructor
public class NullableValueStringValidator implements ConstraintValidator<NullableValue, String> {

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (value == null) return true;
    return StringUtils.hasText(value);
  }

}
