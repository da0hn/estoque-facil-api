package br.com.anunciabem.estoquefacil.services.categories.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.CategorySummaryResponse;
import br.com.anunciabem.estoquefacil.dto.SearchCategoryCriteria;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import br.com.anunciabem.estoquefacil.services.categories.SearchCategoriesUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchCategoriesUseCaseImpl implements SearchCategoriesUseCase {

  private final CategoryRepository categoryRepository;

  @Override
  public Page<CategorySummaryResponse> execute(final SearchCategoryCriteria criteria, final Pageable pageable) {
    log.debug("m=execute(criteria={}, pageable={})", criteria, pageable);
    return this.categoryRepository.findAll(criteria.id(), criteria.name(), criteria.description(), criteria.searchText(), pageable)
      .map(category -> CategorySummaryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .description(category.getDescription())
        .createdAt(category.getCreatedAt())
        .updatedAt(category.getUpdatedAt())
        .createdBy(category.getCreatedBy())
        .modifiedBy(category.getModifiedBy())
        .build()
      );
  }

}
