package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.entities.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
  Long userId,
  String name,
  String username,
  Role role,
  String email,
  String token,
  String refreshToken
) {
}
