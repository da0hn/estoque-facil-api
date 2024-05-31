package br.com.anunciabem.estoquefacil.dto.product;

import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueProductName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ChangeProductParameter(
  @NotNull
  @Positive
  Long productId,
  @NotBlank
  @UniqueProductName
  String name,
  @NullableValue
  String description,
  @PositiveOrZero
  BigDecimal costPrice,
  @PositiveOrZero
  BigDecimal salePrice,
  @NullableValue
  String imei,
  @PositiveOrZero
  Long quantity,
  @NotNull
  @Positive
  Long modelId
) {
}
