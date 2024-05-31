package br.com.anunciabem.estoquefacil.services.products;

import br.com.anunciabem.estoquefacil.dto.product.ProductSummaryResponse;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SearchProductByIdUseCase {

  @Transactional
  ProductSummaryResponse execute(Long productId);

}
