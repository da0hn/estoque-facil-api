package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.entities.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(@NotNull Role role) {
}
