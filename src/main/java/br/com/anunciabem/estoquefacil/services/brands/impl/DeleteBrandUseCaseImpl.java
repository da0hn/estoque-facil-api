package br.com.anunciabem.estoquefacil.services.brands.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.services.brands.DeleteBrandUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class DeleteBrandUseCaseImpl implements DeleteBrandUseCase {

  private final BrandRepository brandRepository;

  @Override
  public void execute(final Long brandId) {
    log.debug("m=execute(brandId={})", brandId);

    ValidationUtils.requireNonNull(brandId, "BrandId cannot be null");

    final var brand = this.brandRepository.findByIdOrElseThrow(brandId);

    this.brandRepository.delete(brand);

    log.info("Brand successfully deleted brandId={}", brand.getId());
  }

}
