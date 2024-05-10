package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.dto.LoginParameter;
import br.com.anunciabem.estoquefacil.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@FunctionalInterface
public interface AuthenticateUseCase {


  @Transactional
  LoginResponse execute(@Valid LoginParameter parameter);

}
