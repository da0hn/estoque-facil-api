package br.com.anunciabem.estoquefacil.services.brands.impl;

import br.com.anunciabem.estoquefacil.annotations.UseCase;
import br.com.anunciabem.estoquefacil.domain.entities.Brand;
import br.com.anunciabem.estoquefacil.dto.CreateBrandParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.repositories.BrandRepository;
import br.com.anunciabem.estoquefacil.repositories.CategoryRepository;
import br.com.anunciabem.estoquefacil.services.brands.CreateBrandUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@AllArgsConstructor
public class CreateBrandUseCaseImpl implements CreateBrandUseCase {

  private final CategoryRepository categoryRepository;

  private final BrandRepository brandRepository;

  @Override
  @Transactional
  public ResourceCreated execute(@Valid final CreateBrandParameter parameter) {
    log.debug("m=execute(parameter={})", parameter);

    final var newBrand = Brand.builder()
      .name(parameter.name())
      .description(parameter.description())
      .category(this.categoryRepository.findByIdOrElseThrow(parameter.categoryId()))
      .build();

    this.brandRepository.save(newBrand);

    log.info("Brand successfully created brandId={} username={}", newBrand.getId(), newBrand.getModifiedBy());

    return new ResourceCreated(newBrand.getId());
  }

}
