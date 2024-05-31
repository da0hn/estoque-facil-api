package br.com.anunciabem.estoquefacil.services.models.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.entities.Model;
import br.com.anunciabem.estoquefacil.dto.model.CreateModelParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.models.CreateModelUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class CreateModelUseCaseImpl implements CreateModelUseCase {

  private final BrandRepository brandRepository;

  private final ModelRepository modelRepository;

  @Override
  public ResourceCreated execute(final CreateModelParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var newModel = Model.builder()
      .name(parameter.name())
      .description(parameter.description())
      .brand(this.brandRepository.findByIdOrElseThrow(parameter.brandId()))
      .build();

    this.modelRepository.save(newModel);

    log.info("Model successfully created modelId={} username={}", newModel.getId(), newModel.getModifiedBy());

    return new ResourceCreated(newModel.getId());
  }

}
