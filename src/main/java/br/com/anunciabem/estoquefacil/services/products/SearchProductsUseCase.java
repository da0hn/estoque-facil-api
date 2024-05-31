package br.com.anunciabem.estoquefacil.services.products;

import br.com.anunciabem.estoquefacil.dto.product.ProductSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.product.SearchProductCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchProductsUseCase {

  @Transactional
  Page<ProductSummaryResponse> execute(SearchProductCriteria criteria, Pageable pageable);

}
