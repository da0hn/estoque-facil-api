package br.com.anunciabem.estoquefacil.services.categories;

import br.com.anunciabem.estoquefacil.dto.category.CreateCategoryParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

public interface CreateCategoryUseCase {

  @Transactional
  ResourceCreated execute(@Valid CreateCategoryParameter parameter);

}
