package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.ApiPageResponse;
import br.com.anunciabem.estoquefacil.dto.model.ChangeModelParameter;
import br.com.anunciabem.estoquefacil.dto.model.ChangeModelRequest;
import br.com.anunciabem.estoquefacil.dto.model.CreateModelParameter;
import br.com.anunciabem.estoquefacil.dto.model.ModelSummaryResponse;
import br.com.anunciabem.estoquefacil.dto.ResourceCreated;
import br.com.anunciabem.estoquefacil.dto.model.SearchModelCriteria;
import br.com.anunciabem.estoquefacil.services.models.ChangeModelUseCase;
import br.com.anunciabem.estoquefacil.services.models.CreateModelUseCase;
import br.com.anunciabem.estoquefacil.services.models.DeleteModelUseCase;
import br.com.anunciabem.estoquefacil.services.models.SearchModelByIdUseCase;
import br.com.anunciabem.estoquefacil.services.models.SearchModelsUseCase;
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
@RequestMapping("/models")
public class ModelController {

  private final SearchModelByIdUseCase searchModelByIdUseCase;

  private final DeleteModelUseCase deleteModelUseCase;

  private final CreateModelUseCase createModelUseCase;

  private final ChangeModelUseCase changeModelUseCase;

  private final SearchModelsUseCase searchModelsUseCase;

  @PostMapping
  public ResponseEntity<ApiDataResponse<ResourceCreated>> createModel(@Valid @RequestBody final CreateModelParameter parameter) {
    final var output = this.createModelUseCase.execute(parameter);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping("/{model-id}")
  public ResponseEntity<ApiDataResponse<ModelSummaryResponse>> searchModelById(
    @PathVariable("model-id") final Long modelId
  ) {
    final var output = this.searchModelByIdUseCase.execute(modelId);
    return ResponseEntity.ok(ApiDataResponse.of(output));
  }

  @GetMapping
  public ResponseEntity<ApiPageResponse<ModelSummaryResponse>> searchModels(
    @RequestParam(name = "model-id", required = false) final Long modelId,
    @RequestParam(name = "name", required = false) final String name,
    @RequestParam(name = "description", required = false) final String description,
    @RequestParam(name = "brand-id", required = false) final Long brandId,
    @RequestParam(name = "brand-name", required = false) final String brandName,
    @RequestParam(name = "search-text", required = false) final String searchText,
    @ParameterObject final Pageable pageable
  ) {
    final var output = this.searchModelsUseCase.execute(
      SearchModelCriteria.builder()
        .modelId(modelId)
        .name(name)
        .description(description)
        .brandId(brandId)
        .brandName(brandName)
        .searchText(searchText)
        .build(),
      pageable
    );

    return ResponseEntity.ok(ApiPageResponse.of(output));
  }

  @PutMapping("/{model-id}")
  public ResponseEntity<ApiDataResponse<Void>> updateModel(
    @PathVariable("model-id") final Long modelId,
    @Valid @RequestBody final ChangeModelRequest request
  ) {
    this.changeModelUseCase.execute(
      ChangeModelParameter.builder()
        .modelId(modelId)
        .name(request.name())
        .description(request.description())
        .brandId(request.brandId())
        .build()
    );
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

  @DeleteMapping("/{model-id}")
  public ResponseEntity<ApiDataResponse<Void>> deleteModel(@PathVariable("model-id") final Long modelId) {
    this.deleteModelUseCase.execute(modelId);
    return ResponseEntity.ok(ApiDataResponse.empty());
  }

}
