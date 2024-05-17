package br.com.anunciabem.estoquefacil.services.brands;

import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface DeleteBrandUseCase {

  @Transactional
  void execute(Long brandId);

}
