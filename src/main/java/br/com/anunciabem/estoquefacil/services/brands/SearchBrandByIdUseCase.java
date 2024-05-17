package br.com.anunciabem.estoquefacil.services.brands;

import br.com.anunciabem.estoquefacil.dto.BrandSummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchBrandByIdUseCase {

  @Transactional
  BrandSummaryResponse execute(Long brandId);

}
