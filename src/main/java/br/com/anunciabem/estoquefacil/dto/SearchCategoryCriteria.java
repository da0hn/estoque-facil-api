package br.com.anunciabem.estoquefacil.dto;

import lombok.Builder;

@Builder
public record SearchCategoryCriteria(
  Long id,
  String name,
  String description,
  String searchText
) {
}
