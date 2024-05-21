package br.com.anunciabem.estoquefacil.services.models;

import br.com.anunciabem.estoquefacil.dto.ChangeModelParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeModelUseCase {

  @Transactional
  void execute(@Valid ChangeModelParameter parameter);

}
