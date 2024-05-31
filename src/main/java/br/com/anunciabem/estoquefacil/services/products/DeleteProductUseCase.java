package br.com.anunciabem.estoquefacil.services.products;

import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface DeleteProductUseCase {

  @Transactional
  void execute(Long productId);

}
