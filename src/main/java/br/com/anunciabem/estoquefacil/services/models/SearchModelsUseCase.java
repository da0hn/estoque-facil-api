package br.com.anunciabem.estoquefacil.services.models;

import br.com.anunciabem.estoquefacil.dto.model.ModelSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.model.SearchModelCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchModelsUseCase {

  @Transactional
  Page<ModelSummaryResponse> execute(SearchModelCriteria criteria, Pageable pageable);

}
