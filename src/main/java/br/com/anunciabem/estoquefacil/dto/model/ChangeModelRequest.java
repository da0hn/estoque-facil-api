package br.com.anunciabem.estoquefacil.dto.model;

import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangeModelRequest(
  @NotBlank
  String name,
  @NullableValue
  String description,
  @NotNull
  @Positive
  Long brandId
) {
}
