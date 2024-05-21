package br.com.anunciabem.estoquefacil.services.models.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import br.com.anunciabem.estoquefacil.dto.ChangeModelParameter;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.models.ChangeModelUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeModelUseCaseImpl implements ChangeModelUseCase {

  private final ModelRepository modelRepository;

  private final BrandRepository brandRepository;

  @Override
  public void execute(final ChangeModelParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var model = this.modelRepository.findByName(parameter.name())
      .orElseGet(() -> this.modelRepository.findByIdOrElseThrow(parameter.modelId()));

    if (!model.getId().equals(parameter.modelId())) {
      throw new BusinessValidationException(String.format("Model with name %s already exists", parameter.name()));
    }

    model.changeName(parameter.name());
    model.changeDescription(parameter.description());
    model.addBrand(this.brandRepository.findByIdOrElseThrow(parameter.brandId()));

    this.modelRepository.save(model);

    log.info("Model successfully updated modelId={} username={}", model.getId(), model.getModifiedBy());
  }

}
