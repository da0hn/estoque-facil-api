package br.com.anunciabem.estoquefacil.dto.user;

import br.com.anunciabem.estoquefacil.domain.entities.Role;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
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
