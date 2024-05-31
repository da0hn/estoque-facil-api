package br.com.anunciabem.estoquefacil.services.models;

import br.com.anunciabem.estoquefacil.dto.model.CreateModelParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface CreateModelUseCase {

  @Transactional
  ResourceCreated execute(@Valid CreateModelParameter parameter);

}
