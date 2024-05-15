package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.entities.Category;
import br.com.anunciabem.estoquefacil.dto.CreateCategoryParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@AllArgsConstructor
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public ResourceCreated execute(@Valid final CreateCategoryParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var newCategory = Category.builder()
      .name(parameter.name())
      .description(parameter.description())
      .build();

    this.categoryRepository.save(newCategory);

    log.info("Category successfully created categoryId={} username={}", newCategory.getId(), newCategory.getModifiedBy());

    return new ResourceCreated(newCategory.getId());
  }

}
