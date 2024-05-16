package br.com.anunciabem.estoquefacil.dto;

import lombok.Builder;

@Builder
public record ResourceSummary(
  Long id,
  String name
) {
}
