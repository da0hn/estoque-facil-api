package br.com.anunciabem.estoquefacil.dto.user;

import br.com.anunciabem.estoquefacil.domain.entities.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChangeUserRoleParameter(
  @NotNull
  Long userId,
  @NotNull
  Role role
) {
}
