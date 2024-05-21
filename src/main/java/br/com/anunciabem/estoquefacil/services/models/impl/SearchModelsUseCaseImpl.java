package br.com.anunciabem.estoquefacil.services.models.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.ModelSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.SearchModelCriteria;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.models.SearchModelsUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchModelsUseCaseImpl implements SearchModelsUseCase {

  private final ModelRepository modelRepository;

  @Override
  public Page<ModelSummaryResponse> execute(final SearchModelCriteria criteria, final Pageable pageable) {
    log.debug("m=execute(criteria={}, pageable={})", criteria, pageable);
    return this.modelRepository.findAll(
      criteria.modelId(),
      criteria.name(),
      criteria.description(),
      criteria.brandId(),
      criteria.brandName(),
      criteria.searchText(),
      pageable
    ).map(ModelSummaryResponse::of);
  }

}
