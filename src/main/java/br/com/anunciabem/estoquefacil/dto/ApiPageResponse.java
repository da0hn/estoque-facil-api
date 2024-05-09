package br.com.anunciabem.estoquefacil.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Value
@Builder
@JsonPropertyOrder({ "timestamp", "pagination", "data" })
public class ApiPageResponse<T> {

  Pagination pagination;

  @Builder.Default
  Instant timestamp = Instant.now();

  @Builder.Default
  Collection<T> data = new ArrayList<>();

  public static <T> ApiPageResponse<T> of(final Page<T> page) {
    return ApiPageResponse.<T>builder()
      .pagination(
        Pagination.builder()
          .pageNumber(page.getNumber())
          .size(page.getSize())
          .totalPages(page.getTotalPages())
          .totalRecords(page.getTotalElements())
          .build()
      )
      .data(page.getContent())
      .build();
  }

  @Value
  @Builder
  @JsonPropertyOrder({ "pageNumber", "size", "totalPages", "totalRecords" })
  public static class Pagination {

    Integer pageNumber;

    Integer size;

    Integer totalPages;

    Long totalRecords;

  }

}
