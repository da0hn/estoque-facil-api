package br.com.anunciabem.estoquefacil.services.users;

import br.com.anunciabem.estoquefacil.dto.user.RegisterUserParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@FunctionalInterface
public interface RegisterUserUseCase {

  @Transactional
  ResourceCreated execute(@Valid final RegisterUserParameter parameter);

}
