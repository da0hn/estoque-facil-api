package br.com.anunciabem.estoquefacil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ChangeCategoryParameter(
  @NotNull
  @Positive
  Long categoryId,
  @NotBlank
  String name,
  String description
) {
}
