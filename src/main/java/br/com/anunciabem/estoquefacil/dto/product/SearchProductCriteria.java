package br.com.anunciabem.estoquefacil.dto.product;

import lombok.Builder;

@Builder
public record SearchProductCriteria(
  Long productId,
  String name,
  String description,
  Long modelId,
  String modelName,
  String searchText
) {
}
