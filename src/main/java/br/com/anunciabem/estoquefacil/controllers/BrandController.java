package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.brand.BrandSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.brand.ChangeBrandParameter;
import br.com.anunciabem.estoquefacil.dto.brand.ChangeBrandRequest;
import br.com.anunciabem.estoquefacil.dto.brand.CreateBrandParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.brand.SearchBrandCriteria;
import br.com.anunciabem.estoquefacil.services.brands.ChangeBrandUseCase;
import br.com.anunciabem.estoquefacil.services.brands.CreateBrandUseCase;
import br.com.anunciabem.estoquefacil.services.brands.DeleteBrandUseCase;
import br.com.anunciabem.estoquefacil.services.brands.SearchBrandByIdUseCase;
import br.com.anunciabem.estoquefacil.services.brands.SearchBrandsUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/brands")
public class BrandController {

  private final SearchBrandByIdUseCase searchBrandByIdUseCase;

  private final DeleteBrandUseCase deleteBrandUseCase;

  private final CreateBrandUseCase createBrandUseCase;

  private final ChangeBrandUseCase changeBrandUseCase;

  private final SearchBrandsUseCase searchBrandsUseCase;

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> createBrand(@Valid @RequestBody final CreateBrandParameter parameter) {
    final var output = this.createBrandUseCase.execute(parameter);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping("/{brand-id}")
  public ResponseEntity<ApiDataResponse<BrandSummaryResponse>> searchBrandById(
    @PathVariable("brand-id") final Long brandId
  ) {
    final var output = this.searchBrandByIdUseCase.execute(brandId);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping
  public ResponseEntity<ApiPageResponse<BrandSummaryResponse>> searchBrands(
    @RequestParam(name = "brand-id", required = false) final Long brandId,
    @RequestParam(name = "name", required = false) final String name,
    @RequestParam(name = "description", required = false) final String description,
    @RequestParam(name = "category-id", required = false) final Long categoryId,
    @RequestParam(name = "category-name", required = false) final String categoryName,
    @RequestParam(name = "search-text", required = false) final String searchText,
    @ParameterObject final Pageable pageable
  ) {
    final var output = this.searchBrandsUseCase.execute(
      SearchBrandCriteria.builder()
        .brandId(brandId)
        .name(name)
        .description(description)
        .categoryId(categoryId)
        .categoryName(categoryName)
        .searchText(searchText)
        .build(),
      pageable
    );

    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

  @PutMapping("/{brand-id}")
  public ResponseEntity<ApiDataResponse<Void>> updateBrand(
    @PathVariable("brand-id") final Long brandId,
    @Valid @RequestBody final ChangeBrandRequest request
  ) {
    this.changeBrandUseCase.execute(
      ChangeBrandParameter.builder()
        .brandId(brandId)
        .name(request.name())
        .description(request.description())
        .categoryId(request.categoryId())
        .build()
    );
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

  @DeleteMapping("/{brand-id}")
  public ResponseEntity<ApiDataResponse<Void>> deleteBrand(@PathVariable("brand-id") final Long brandId) {
    this.deleteBrandUseCase.execute(brandId);
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

}
