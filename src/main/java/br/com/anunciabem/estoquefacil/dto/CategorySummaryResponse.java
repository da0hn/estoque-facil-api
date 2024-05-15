package br.com.anunciabem.estoquefacil.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record CategorySummaryResponse(
  Long id,
  String name,
  String description,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt,
  String modifiedBy,
  String createdBy
) {
}
