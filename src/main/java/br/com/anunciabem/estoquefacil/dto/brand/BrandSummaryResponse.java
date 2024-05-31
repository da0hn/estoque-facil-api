package br.com.anunciabem.estoquefacil.dto.brand;

import br.com.anunciabem.estoquefacil.domain.entities.Brand;
import br.com.anunciabem.estoquefacil.dto.ResourceSummary;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record BrandSummaryResponse(
  Long id,
  String name,
  String description,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt,
  String modifiedBy,
  String createdBy,
  ResourceSummary category
) {

  public static BrandSummaryResponse of(final Brand brand) {
    return BrandSummaryResponse.builder()
      .id(brand.getId())
      .name(brand.getName())
      .description(brand.getDescription())
      .category(
        ResourceSummary.builder()
          .id(brand.getCategory().getId())
          .name(brand.getCategory().getName())
          .build()
      )
      .createdBy(brand.getCreatedBy())
      .modifiedBy(brand.getModifiedBy())
      .createdAt(brand.getCreatedAt())
      .updatedAt(brand.getUpdatedAt())
      .build();
  }

}
