package br.com.anunciabem.estoquefacil.services.categories.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import br.com.anunciabem.estoquefacil.dto.category.ChangeCategoryParameter;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import br.com.anunciabem.estoquefacil.services.categories.ChangeCategoryUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeCategoryUseCaseImpl implements ChangeCategoryUseCase {

  private final CategoryRepository categoryRepository;

  @Override
  public void execute(final ChangeCategoryParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var category = this.categoryRepository.findByName(parameter.name())
      .orElseGet(() -> this.categoryRepository.findByIdOrElseThrow(parameter.categoryId()));

    if (!category.getId().equals(parameter.categoryId())) {
      throw new BusinessValidationException(String.format("Category with name %s already exists", parameter.name()));
    }

    category.changeName(parameter.name());
    category.changeDescription(parameter.description());

    this.categoryRepository.save(category);

    log.info("Category successfully updated categoryId={} username={}", category.getId(), category.getModifiedBy());
  }

}
