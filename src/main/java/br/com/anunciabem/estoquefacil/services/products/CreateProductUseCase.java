package br.com.anunciabem.estoquefacil.services.products;

import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.product.CreateProductParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface CreateProductUseCase {

  @Transactional
  ResourceCreated execute(@Valid CreateProductParameter parameter);

}
