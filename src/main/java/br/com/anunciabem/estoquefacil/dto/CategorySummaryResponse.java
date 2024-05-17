package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.entities.Category;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record CategorySummaryResponse(
  Long id,
  String name,
  String description,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt,
  String modifiedBy,
  String createdBy
) {

  public static CategorySummaryResponse of(final Category category) {
    return CategorySummaryResponse.builder()
      .id(category.getId())
      .name(category.getName())
      .description(category.getDescription())
      .createdAt(category.getCreatedAt())
      .updatedAt(category.getUpdatedAt())
      .createdBy(category.getCreatedBy())
      .modifiedBy(category.getModifiedBy())
      .build();
  }

}
