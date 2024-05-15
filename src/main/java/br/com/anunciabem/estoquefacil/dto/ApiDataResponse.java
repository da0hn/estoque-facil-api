package br.com.anunciabem.estoquefacil.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
@JsonPropertyOrder({ "message", "timestamp", "data" })
public class ApiDataResponse<T> {

  T data;

  String message;

  @Builder.Default
  Instant timestamp = Instant.now();

  public static ApiDataResponse<Void> onlyMessage(final String message) {
    return ApiDataResponse.<Void>builder()
      .message(message)
      .build();
  }

  public static <T> ApiDataResponse<T> of(final T data) {
    return ApiDataResponse.<T>builder()
      .message("Success")
      .data(data)
      .build();
  }

  public static ApiDataResponse<Void> failure(final String message) {
    return ApiDataResponse.<Void>builder()
      .data(null)
      .timestamp(Instant.now())
      .message(message)
      .build();
  }

  public static ApiDataResponse<Void> empty() {
    return ApiDataResponse.<Void>builder()
      .data(null)
      .timestamp(Instant.now())
      .message("Success")
      .build();
  }

}
