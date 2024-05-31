package br.com.anunciabem.estoquefacil.dto.category;

import jakarta.validation.constraints.NotBlank;

public record ChangeCategoryRequest(
  @NotBlank
  String name,
  String description
) {
}
