package br.com.anunciabem.estoquefacil.services.brands.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.BrandSummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.services.brands.SearchBrandByIdUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchBrandByIdUseCaseImpl implements SearchBrandByIdUseCase {

  private final BrandRepository brandRepository;

  @Override
  public BrandSummaryResponse execute(final Long brandId) {
    log.debug("m=execute(brandId={})", brandId);
    final var brand = this.brandRepository.findByIdOrElseThrow(brandId);
    return BrandSummaryResponse.of(brand);
  }

}

