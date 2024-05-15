package br.com.anunciabem.estoquefacil.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeCategoryRequest(
  @NotBlank
  String name,
  String description
) {
}
