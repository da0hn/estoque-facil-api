package br.com.anunciabem.estoquefacil.services.models.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.models.DeleteModelUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class DeleteModelUseCaseImpl implements DeleteModelUseCase {

  private final ModelRepository modelRepository;

  @Override
  public void execute(final Long modelId) {
    log.debug("m=execute(modelId={})", modelId);

    ValidationUtils.requireNonNull(modelId, "ModelId cannot be null");

    final var model = this.modelRepository.findByIdOrElseThrow(modelId);

    this.modelRepository.delete(model);

    log.info("Model successfully deleted modelId={}", model.getId());
  }

}
