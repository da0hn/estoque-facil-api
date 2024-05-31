package br.com.anunciabem.estoquefacil.dto.category;

import br.com.anunciabem.estoquefacil.domain.constraints.UniqueCategoryName;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryParameter(
  @NotBlank
  @UniqueCategoryName
  String name,
  String description
) {
}
