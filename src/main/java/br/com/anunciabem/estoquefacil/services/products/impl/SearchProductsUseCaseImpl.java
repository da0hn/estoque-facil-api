package br.com.anunciabem.estoquefacil.services.products.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.product.ProductSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.product.SearchProductCriteria;
import br.com.anunciabem.estoquefacil.repositories.ProductRepository;
import br.com.anunciabem.estoquefacil.services.products.SearchProductsUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchProductsUseCaseImpl implements SearchProductsUseCase {

  private final ProductRepository productRepository;

  @Override
  public Page<ProductSummaryResponse> execute(final SearchProductCriteria criteria, final Pageable pageable) {
    log.debug("m=execute(criteria={}, pageable={})", criteria, pageable);
    return this.productRepository.findAll(
      criteria.productId(),
      criteria.name(),
      criteria.description(),
      criteria.modelId(),
      criteria.modelName(),
      criteria.searchText(),
      pageable
    ).map(ProductSummaryResponse::of);
  }

}
