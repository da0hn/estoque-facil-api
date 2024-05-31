package br.com.anunciabem.estoquefacil.services.products.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.repositories.ProductRepository;
import br.com.anunciabem.estoquefacil.services.products.DeleteProductUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

  private final ProductRepository productRepository;

  @Override
  public void execute(final Long productId) {
    log.debug("m=execute(productId={})", productId);

    ValidationUtils.requireNonNull(productId, "ProductId cannot be null");

    final var product = this.productRepository.findByIdOrElseThrow(productId);

    this.productRepository.delete(product);

    log.info("Product successfully deleted productId={}", product.getId());
  }

}
