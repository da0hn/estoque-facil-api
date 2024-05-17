package br.com.anunciabem.estoquefacil.services.brands;

import br.com.anunciabem.estoquefacil.dto.ChangeBrandParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeBrandUseCase {

  @Transactional
  void execute(@Valid ChangeBrandParameter parameter);

}
