package br.com.anunciabem.estoquefacil.dto.product;

import br.com.anunciabem.estoquefacil.domain.constraints.NullableValue;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueProductName;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductParameter(
  @NotBlank
  @UniqueProductName
  String name,
  @NullableValue
  String description,
  @PositiveOrZero
  @Digits(integer = 10, fraction = 2)
  BigDecimal costPrice,
  @PositiveOrZero
  @Digits(integer = 10, fraction = 2)
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
