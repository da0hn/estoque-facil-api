package br.com.anunciabem.estoquefacil.services.categories.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import br.com.anunciabem.estoquefacil.services.categories.DeleteCategoryUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

  private final CategoryRepository categoryRepository;

  @Override
  public void execute(final Long categoryId) {
    log.debug("m=execute(categoryId={})", categoryId);
    ValidationUtils.requireNonNull(categoryId, "CategoryId cannot be null");

    final var category = this.categoryRepository.findByIdOrElseThrow(categoryId);

    this.categoryRepository.delete(category);
  }

}
