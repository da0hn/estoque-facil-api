package br.com.anunciabem.estoquefacil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeUserPasswordRequest(
  @NotBlank
  @Size(min = 6)
  String password
) {
}
