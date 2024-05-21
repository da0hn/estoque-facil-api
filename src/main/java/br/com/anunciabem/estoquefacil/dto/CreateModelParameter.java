package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueBrandName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateModelParameter(
  @NotBlank
  @UniqueBrandName
  String name,
  @NullableValue
  String description,
  @NotNull
  @Positive
  Long brandId
) {
}
