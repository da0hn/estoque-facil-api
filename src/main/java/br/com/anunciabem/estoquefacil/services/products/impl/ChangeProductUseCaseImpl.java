package br.com.anunciabem.estoquefacil.services.products.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.repositories.ProductRepository;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import br.com.anunciabem.estoquefacil.dto.product.ChangeProductParameter;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.products.ChangeProductUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class ChangeProductUseCaseImpl implements ChangeProductUseCase {

  private final ProductRepository productRepository;

  private final ModelRepository modelRepository;

  @Override
  public void execute(final ChangeProductParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var product = this.productRepository.findByName(parameter.name())
      .orElseGet(() -> this.productRepository.findByIdOrElseThrow(parameter.productId()));

    if (!product.getId().equals(parameter.productId())) {
      throw new BusinessValidationException(String.format("Product with name %s already exists", parameter.name()));
    }

    product.changeName(parameter.name());
    product.changeDescription(parameter.description());
    product.changeCostPrice(parameter.costPrice());
    product.changeSalePrice(parameter.salePrice());
    product.changeQuantity(parameter.quantity());
    product.changeImei(parameter.imei());

    product.addModel(this.modelRepository.findByIdOrElseThrow(parameter.modelId()));

    this.productRepository.save(product);

    log.info("Product successfully updated productId={} username={}", product.getId(), product.getModifiedBy());
  }

}
