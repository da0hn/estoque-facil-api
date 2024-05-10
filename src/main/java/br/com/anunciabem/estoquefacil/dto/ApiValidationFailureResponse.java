package br.com.anunciabem.estoquefacil.dto;

public record ApiValidationFailureResponse(
  String field,
  String value,
  String message
) {
}
