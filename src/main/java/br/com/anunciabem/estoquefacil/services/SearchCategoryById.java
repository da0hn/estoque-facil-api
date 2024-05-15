package br.com.anunciabem.estoquefacil.services;

import br.com.anunciabem.estoquefacil.dto.CategorySummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchCategoryById {

  @Transactional
  CategorySummaryResponse execute(final Long id);

}
