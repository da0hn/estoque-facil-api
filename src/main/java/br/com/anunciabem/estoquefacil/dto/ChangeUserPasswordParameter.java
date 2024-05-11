package br.com.anunciabem.estoquefacil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ChangeUserPasswordParameter(
  @NotNull
  Long userId,
  @NotBlank
  @Size(min = 6)
  String password
) {
}
