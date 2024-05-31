package br.com.anunciabem.estoquefacil.dto.category;

import lombok.Builder;

@Builder
public record SearchCategoryCriteria(
  Long id,
  String name,
  String description,
  String searchText
) {
}
