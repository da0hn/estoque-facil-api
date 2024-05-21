package br.com.anunciabem.estoquefacil.services.models;

import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface DeleteModelUseCase {

  @Transactional
  void execute(Long modelId);

}
