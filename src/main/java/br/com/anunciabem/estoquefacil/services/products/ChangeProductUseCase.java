package br.com.anunciabem.estoquefacil.services.products;

import br.com.anunciabem.estoquefacil.dto.product.ChangeProductParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeProductUseCase {

  @Transactional
  void execute(@Valid ChangeProductParameter parameter);

}
