package br.com.anunciabem.estoquefacil.dto.model;

import br.com.anunciabem.estoquefacil.domain.entities.Model;
import br.com.anunciabem.estoquefacil.dto.ResourceSummary;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record ModelSummaryResponse(
  Long id,
  String name,
  String description,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt,
  String modifiedBy,
  String createdBy,
  ResourceSummary brand
) {

  public static ModelSummaryResponse of(final Model model) {
    return ModelSummaryResponse.builder()
      .id(model.getId())
      .name(model.getName())
      .description(model.getDescription())
      .brand(
        ResourceSummary.builder()
          .id(model.getBrand().getId())
          .name(model.getBrand().getName())
          .build()
      )
      .createdBy(model.getCreatedBy())
      .modifiedBy(model.getModifiedBy())
      .createdAt(model.getCreatedAt())
      .updatedAt(model.getUpdatedAt())
      .build();
  }

}
