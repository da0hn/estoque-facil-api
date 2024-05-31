package br.com.anunciabem.estoquefacil.services.models;

import br.com.anunciabem.estoquefacil.dto.model.ModelSummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchModelByIdUseCase {

  @Transactional
  ModelSummaryResponse execute(Long modelId);

}
