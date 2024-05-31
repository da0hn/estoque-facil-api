package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.category.CategorySummaryResponse;
import br.com.anunciabem.estoquefacil.dto.category.ChangeCategoryParameter;
import br.com.anunciabem.estoquefacil.dto.category.ChangeCategoryRequest;
import br.com.anunciabem.estoquefacil.dto.category.CreateCategoryParameter;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.category.SearchCategoryCriteria;
import br.com.anunciabem.estoquefacil.services.categories.ChangeCategoryUseCase;
import br.com.anunciabem.estoquefacil.services.categories.CreateCategoryUseCase;
import br.com.anunciabem.estoquefacil.services.categories.DeleteCategoryUseCase;
import br.com.anunciabem.estoquefacil.services.categories.SearchCategoriesUseCase;
import br.com.anunciabem.estoquefacil.services.categories.SearchCategoryById;
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
@RequestMapping("/categories")
public class CategoryController {

  private final CreateCategoryUseCase createCategoryUseCase;

  private final ChangeCategoryUseCase changeCategoryUseCase;

  private final SearchCategoriesUseCase searchCategoriesUseCase;

  private final SearchCategoryById searchCategoryById;

  private final DeleteCategoryUseCase deleteCategoryUseCase;

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> createCategory(@Valid @RequestBody final CreateCategoryParameter parameter) {
    final var output = this.createCategoryUseCase.execute(parameter);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @PutMapping("/{category-id}")
  public ResponseEntity<ApiDataResponse<Void>> updateCategory(
    @PathVariable("category-id") final Long categoryId,
    @Valid @RequestBody final ChangeCategoryRequest request
  ) {
    this.changeCategoryUseCase.execute(
      ChangeCategoryParameter.builder()
        .categoryId(categoryId)
        .name(request.name())
        .description(request.description())
        .build()
    );
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

  @GetMapping
  public ResponseEntity<ApiPageResponse<CategorySummaryResponse>> searchCategories(
    @RequestParam(name = "id", required = false) final Long categoryId,
    @RequestParam(name = "description", required = false) final String description,
    @RequestParam(name = "name", required = false) final String name,
    @RequestParam(name = "search-text", required = false) final String searchText,
    @ParameterObject final Pageable pageable
  ) {
    final var output = this.searchCategoriesUseCase.execute(
      SearchCategoryCriteria.builder()
        .id(categoryId)
        .description(description)
        .name(name)
        .searchText(searchText)
        .build(),
      pageable
    );
    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

  @GetMapping("/{category-id}")
  public ResponseEntity<ApiDataResponse<CategorySummaryResponse>> searchCategories(@PathVariable("category-id") final Long categoryId) {
    final var output = this.searchCategoryById.execute(categoryId);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @DeleteMapping("/{category-id}")
  public ResponseEntity<ApiDataResponse<Void>> deleteCategory(@PathVariable("category-id") final Long categoryId) {
    this.deleteCategoryUseCase.execute(categoryId);
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

}
