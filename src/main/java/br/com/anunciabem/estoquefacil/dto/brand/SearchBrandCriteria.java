package br.com.anunciabem.estoquefacil.dto.brand;

import lombok.Builder;

@Builder
public record SearchBrandCriteria(
  Long brandId,
  String name,
  String description,
  Long categoryId,
  String categoryName,
  String searchText
) {
}
