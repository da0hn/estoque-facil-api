package br.com.anunciabem.estoquefacil.services.users;

import org.springframework.transaction.annotation.Transactional;


public interface DeleteUserUseCase {

  @Transactional
  void execute(final Long userId);

}
