package br.com.anunciabem.estoquefacil.services.categories;

import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface DeleteCategoryUseCase {

  @Transactional
  void execute(final Long userId);

}
