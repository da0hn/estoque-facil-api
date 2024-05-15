package br.com.anunciabem.estoquefacil.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Value
@Builder
@JsonPropertyOrder({ "message", "timestamp", "status", "statusCode", "data" })
public class ApiFailureResponse<T> {

  T data;

  String message;

  @Builder.Default
  Instant timestamp = Instant.now();

  HttpStatus status;

  Integer statusCode;

  public static <T> ApiFailureResponse<T> of(final String message, final HttpStatus status, final T data) {
    return ApiFailureResponse.<T>builder()
      .message(message)
      .status(status)
      .data(data)
      .statusCode(status.value())
      .timestamp(Instant.now())
      .build();
  }

  public static ApiFailureResponse<Void> of(final String message, final HttpStatus status) {
    return ApiFailureResponse.<Void>builder()
      .message(message)
      .status(status)
      .data(null)
      .statusCode(status.value())
      .timestamp(Instant.now())
      .build();
  }

}
