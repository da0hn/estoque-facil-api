package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.product.ChangeProductParameter;
import br.com.anunciabem.estoquefacil.dto.product.ChangeProductRequest;
import br.com.anunciabem.estoquefacil.dto.product.CreateProductParameter;
import br.com.anunciabem.estoquefacil.dto.product.ProductSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.product.SearchProductCriteria;
import br.com.anunciabem.estoquefacil.services.products.ChangeProductUseCase;
import br.com.anunciabem.estoquefacil.services.products.CreateProductUseCase;
import br.com.anunciabem.estoquefacil.services.products.DeleteProductUseCase;
import br.com.anunciabem.estoquefacil.services.products.SearchProductByIdUseCase;
import br.com.anunciabem.estoquefacil.services.products.SearchProductsUseCase;
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
@RequestMapping("/products")
public class ProductController {

  private final SearchProductByIdUseCase searchProductByIdUseCase;

  private final DeleteProductUseCase deleteProductUseCase;

  private final CreateProductUseCase createProductUseCase;

  private final ChangeProductUseCase changeProductUseCase;

  private final SearchProductsUseCase searchProductsUseCase;

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> createProduct(@Valid @RequestBody final CreateProductParameter parameter) {
    final var output = this.createProductUseCase.execute(parameter);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping("/{product-id}")
  public ResponseEntity<ApiDataResponse<ProductSummaryResponse>> searchProductById(
    @PathVariable("product-id") final Long productId
  ) {
    final var output = this.searchProductByIdUseCase.execute(productId);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping
  public ResponseEntity<ApiPageResponse<ProductSummaryResponse>> searchProducts(
    @RequestParam(name = "product-id", required = false) final Long productId,
    @RequestParam(name = "name", required = false) final String name,
    @RequestParam(name = "description", required = false) final String description,
    @RequestParam(name = "model-id", required = false) final Long modelId,
    @RequestParam(name = "model-name", required = false) final String modelName,
    @RequestParam(name = "search-text", required = false) final String searchText,
    @ParameterObject final Pageable pageable
  ) {
    final var output = this.searchProductsUseCase.execute(
      SearchProductCriteria.builder()
        .productId(productId)
        .name(name)
        .description(description)
        .modelId(modelId)
        .modelName(modelName)
        .searchText(searchText)
        .build(),
      pageable
    );

    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

  @PutMapping("/{product-id}")
  public ResponseEntity<ApiDataResponse<Void>> updateProduct(
    @PathVariable("product-id") final Long productId,
    @Valid @RequestBody final ChangeProductRequest request
  ) {
    this.changeProductUseCase.execute(
      ChangeProductParameter.builder()
        .productId(productId)
        .name(request.name())
        .description(request.description())
        .modelId(request.modelId())
        .costPrice(request.costPrice())
        .salePrice(request.salePrice())
        .quantity(request.quantity())
        .imei(request.imei())
        .build()
    );
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

  @DeleteMapping("/{product-id}")
  public ResponseEntity<ApiDataResponse<Void>> deleteProduct(@PathVariable("product-id") final Long productId) {
    this.deleteProductUseCase.execute(productId);
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

}
