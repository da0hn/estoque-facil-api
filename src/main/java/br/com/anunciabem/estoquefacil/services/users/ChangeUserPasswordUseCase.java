package br.com.anunciabem.estoquefacil.services.users;

import br.com.anunciabem.estoquefacil.dto.ChangeUserPasswordParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeUserPasswordUseCase {

  @Transactional
  void execute(@Valid ChangeUserPasswordParameter parameter);

}
