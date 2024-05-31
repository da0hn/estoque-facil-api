package br.com.anunciabem.estoquefacil.services.categories;

import br.com.anunciabem.estoquefacil.dto.category.CategorySummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchCategoryById {

  @Transactional
  CategorySummaryResponse execute(final Long id);

}
