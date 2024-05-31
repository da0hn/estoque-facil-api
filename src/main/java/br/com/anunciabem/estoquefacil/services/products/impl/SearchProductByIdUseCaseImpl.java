package br.com.anunciabem.estoquefacil.services.products.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.dto.product.ProductSummaryResponse;
import br.com.anunciabem.estoquefacil.repositories.ProductRepository;
import br.com.anunciabem.estoquefacil.services.products.SearchProductByIdUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class SearchProductByIdUseCaseImpl implements SearchProductByIdUseCase {

  private final ProductRepository productRepository;

  @Override
  public ProductSummaryResponse execute(final Long productId) {
    log.debug("m=execute(productId={})", productId);
    final var product = this.productRepository.findByIdOrElseThrow(productId);
    return ProductSummaryResponse.of(product);
  }

}
