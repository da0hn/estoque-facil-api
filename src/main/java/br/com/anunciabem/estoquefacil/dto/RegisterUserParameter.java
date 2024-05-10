package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.constraints.UniqueEmail;
import br.com.anunciabem.estoquefacil.domain.constraints.UniqueUsername;
import br.com.anunciabem.estoquefacil.domain.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserParameter(
  @NotBlank
  @Size(max = 50)
  String name,
  @Email
  @NotNull
  @UniqueEmail
  @Size(max = 120)
  String email,
  @NotBlank
  @UniqueUsername
  @Size(max = 50)
  String username,
  @NotBlank
  String password,
  @NotBlank
  String confirmationPassword,
  @NotNull
  Role role
) {
}
