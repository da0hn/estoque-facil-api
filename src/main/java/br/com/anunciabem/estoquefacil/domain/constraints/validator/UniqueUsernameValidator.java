package br.com.anunciabem.estoquefacil.domain.constraints.validator;

import br.com.anunciabem.estoquefacil.annotations.Validator;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueUsername;
import br.com.anunciabem.estoquefacil.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@Validator
@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private final UserRepository userRepository;

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return value != null && !this.userRepository.existsByUsername(value);
  }

}
