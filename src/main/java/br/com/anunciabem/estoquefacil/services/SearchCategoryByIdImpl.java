package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.CategorySummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchCategoryByIdImpl implements SearchCategoryById {

  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public CategorySummaryResponse execute(@Valid @NotNull final Long categoryId) {
    log.debug("m=execute(categoryId={})", categoryId);
    final var category = this.categoryRepository.findByIdOrElseThrow(categoryId);
    return CategorySummaryResponse.builder()
      .id(category.getId())
      .name(category.getName())
      .description(category.getDescription())
      .createdAt(category.getCreatedAt())
      .updatedAt(category.getUpdatedAt())
      .createdBy(category.getCreatedBy())
      .modifiedBy(category.getModifiedBy())
      .build();
  }

}
