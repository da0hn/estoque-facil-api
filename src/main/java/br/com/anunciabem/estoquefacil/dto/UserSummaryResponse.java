package br.com.anunciabem.estoquefacil.dto;

import br.com.anunciabem.estoquefacil.domain.entities.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

public record UserSummaryResponse(
  Long id,
  String name,
  String username,
  String email,
  Role role,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt
) {
}
