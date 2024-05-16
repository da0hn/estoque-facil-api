package br.com.anunciabem.estoquefacil.services.users;

import br.com.anunciabem.estoquefacil.dto.ChangeUserRoleParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeUserRoleUseCase {

  @Transactional
  void execute(@Valid ChangeUserRoleParameter parameter);

}
