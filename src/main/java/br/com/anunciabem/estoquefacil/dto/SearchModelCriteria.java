package br.com.anunciabem.estoquefacil.dto;

import lombok.Builder;

@Builder
public record SearchModelCriteria(
  Long modelId,
  String name,
  String description,
  Long brandId,
  String brandName,
  String searchText
) {
}
