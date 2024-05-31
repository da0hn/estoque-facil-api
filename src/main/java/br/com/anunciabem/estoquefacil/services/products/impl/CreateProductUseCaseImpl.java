package br.com.anunciabem.estoquefacil.services.products.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.entities.Product;
import br.com.anunciabem.estoquefacil.repositories.ProductRepository;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.product.CreateProductParameter;
import br.com.anunciabem.estoquefacil.repositories.ModelRepository;
import br.com.anunciabem.estoquefacil.services.products.CreateProductUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

  private final ModelRepository modelRepository;

  private final ProductRepository productRepository;

  @Override
  public ResourceCreated execute(final CreateProductParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var newProduct = Product.builder()
      .name(parameter.name())
      .description(parameter.description())
      .costPrice(parameter.costPrice())
      .salePrice(parameter.salePrice())
      .quantity(parameter.quantity())
      .imei(parameter.imei())
      .model(this.modelRepository.findByIdOrElseThrow(parameter.modelId()))
      .build();

    this.productRepository.save(newProduct);

    log.info("Product successfully created productId={} username={}", newProduct.getId(), newProduct.getModifiedBy());

    return new ResourceCreated(newProduct.getId());
  }

}
