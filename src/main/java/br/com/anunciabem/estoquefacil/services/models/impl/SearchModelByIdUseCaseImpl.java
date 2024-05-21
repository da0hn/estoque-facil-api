package br.com.anunciabem.estoquefacil.services.models.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.ModelSummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.models.SearchModelByIdUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchModelByIdUseCaseImpl implements SearchModelByIdUseCase {

  private final ModelRepository modelRepository;

  @Override
  public ModelSummaryResponse execute(final Long modelId) {
    log.debug("m=execute(modelId={})", modelId);
    final var model = this.modelRepository.findByIdOrElseThrow(modelId);
    return ModelSummaryResponse.of(model);
  }

}
