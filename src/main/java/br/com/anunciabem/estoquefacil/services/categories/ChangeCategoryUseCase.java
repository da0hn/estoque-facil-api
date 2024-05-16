package br.com.anunciabem.estoquefacil.services.categories;

import br.com.anunciabem.estoquefacil.dto.ChangeCategoryParameter;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface ChangeCategoryUseCase {

  @Transactional
  void execute(@Valid ChangeCategoryParameter parameter);

}
