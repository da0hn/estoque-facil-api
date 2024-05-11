package br.com.anunciabem.estoquefacil.services;

import org.springframework.transaction.annotation.Transactional;


public interface DeleteUserUseCase {

  @Transactional
  void execute(final Long userId);

}
