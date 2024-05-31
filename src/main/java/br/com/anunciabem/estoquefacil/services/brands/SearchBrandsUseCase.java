package br.com.anunciabem.estoquefacil.services.brands;

import br.com.anunciabem.estoquefacil.dto.brand.BrandSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.brand.SearchBrandCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchBrandsUseCase {

  @Transactional
  Page<BrandSummaryResponse> execute(SearchBrandCriteria criteria, Pageable pageable);

}
