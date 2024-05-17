package br.com.anunciabem.estoquefacil.services.brands.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.BrandSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.SearchBrandCriteria;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.services.brands.SearchBrandsUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchBrandsUseCaseImpl implements SearchBrandsUseCase {

  private final BrandRepository brandRepository;

  @Override
  public Page<BrandSummaryResponse> execute(final SearchBrandCriteria criteria, final Pageable pageable) {
    log.debug("m=execute(criteria={}, pageable={})", criteria, pageable);
    return this.brandRepository.findAll(
      criteria.brandId(),
      criteria.name(),
      criteria.description(),
      criteria.categoryId(),
      criteria.categoryName(),
      criteria.searchText(),
      pageable
    ).map(BrandSummaryResponse::of);
  }

}
