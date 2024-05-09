package br.com.anunciabem.estoquefacil.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Value
@Builder
@JsonPropertyOrder({ "message", "timestamp", "data" })
public class ApiCollectionResponse<T> {

  @Builder.Default
  Collection<T> data = new ArrayList<>();

  String message;

  @Builder.Default
  Instant timestamp = Instant.now();

  public static <T extends Serializable> ApiCollectionResponse<T> empty() {
    return ApiCollectionResponse.<T>builder()
      .build();
  }

}
