package br.com.anunciabem.estoquefacil.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginParameter(
  @NotBlank
  String username,
  @NotBlank
  String password
) {
}
