package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueModelName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ChangeModelParameter(
  @NotNull
  @Positive
  Long modelId,
  @NotBlank
  @UniqueModelName
  String name,
  @NullableValue
  String description,
  @NotNull
  @Positive
  Long brandId
) {
}
