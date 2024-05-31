package br.com.anunciabem.estoquefacil.services.brands;

import br.com.anunciabem.estoquefacil.dto.brand.CreateBrandParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface CreateBrandUseCase {

  @Transactional
  ResourceCreated execute(@Valid CreateBrandParameter parameter);

}
