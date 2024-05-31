package br.com.anunciabem.estoquefacil.services.users;

import br.com.anunciabem.estoquefacil.dto.user.LoginParameter;
import br.com.anunciabem.estoquefacil.dto.user.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@FunctionalInterface
public interface AuthenticateUseCase {


  @Transactional
  LoginResponse execute(@Valid LoginParameter parameter);

}
