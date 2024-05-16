package br.com.anunciabem.estoquefacil.services.categories;

import br.com.anunciabem.estoquefacil.dto.CategorySummaryResponse;
import br.com.anunciabem.estoquefacil.dto.SearchCategoryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchCategoriesUseCase {

  @Transactional
  Page<CategorySummaryResponse> execute(
    SearchCategoryCriteria criteria,
    Pageable pageable
  );

}
