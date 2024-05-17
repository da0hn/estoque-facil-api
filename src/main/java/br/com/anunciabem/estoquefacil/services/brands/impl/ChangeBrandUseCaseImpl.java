package br.com.anunciabem.estoquefacil.services.brands.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import br.com.anunciabem.estoquefacil.dto.ChangeBrandParameter;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import br.com.anunciabem.estoquefacil.services.brands.ChangeBrandUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeBrandUseCaseImpl implements ChangeBrandUseCase {

  private final CategoryRepository categoryRepository;

  private final BrandRepository brandRepository;

  @Override
  public void execute(final ChangeBrandParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var brand = this.brandRepository.findByName(parameter.name())
      .orElseGet(() -> this.brandRepository.findByIdOrElseThrow(parameter.brandId()));

    if (!brand.getId().equals(parameter.brandId())) {
      throw new BusinessValidationException(String.format("Brand with name %s already exists", parameter.name()));
    }

    brand.changeName(parameter.name());
    brand.changeDescription(parameter.description());
    brand.addCategory(this.categoryRepository.findByIdOrElseThrow(parameter.categoryId()));

    this.brandRepository.save(brand);

    log.info("Brand successfully updated brandId={} username={}", brand.getId(), brand.getModifiedBy());
  }

}
