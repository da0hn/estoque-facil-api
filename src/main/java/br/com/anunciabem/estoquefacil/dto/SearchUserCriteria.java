package br.com.anunciabem.estoquefacil.dto;

import lombok.Builder;

@Builder
public record SearchUserCriteria(
  String name,
  String username,
  String email,
  String searchText
) {
}
