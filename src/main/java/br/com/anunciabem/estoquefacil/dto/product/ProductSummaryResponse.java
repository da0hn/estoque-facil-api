package br.com.anunciabem.estoquefacil.dto.product;

import br.com.anunciabem.estoquefacil.domain.entities.Product;
import br.com.anunciabem.estoquefacil.dto.ResourceSummary;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record ProductSummaryResponse(
  Long id,
  String name,
  String description,
  BigDecimal costPrice,
  BigDecimal salePrice,
  String imei,
  Long quantity,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant createdAt,
  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  Instant updatedAt,
  String modifiedBy,
  String createdBy,
  ResourceSummary model
) {

  public static ProductSummaryResponse of(final Product product) {
    return ProductSummaryResponse.builder()
      .id(product.getId())
      .name(product.getName())
      .description(product.getDescription())
      .costPrice(product.getCostPrice())
      .salePrice(product.getSalePrice())
      .imei(product.getImei())
      .quantity(product.getQuantity())
      .model(
        ResourceSummary.builder()
          .id(product.getModel().getId())
          .name(product.getModel().getName())
          .build()
      )
      .createdBy(product.getCreatedBy())
      .modifiedBy(product.getModifiedBy())
      .createdAt(product.getCreatedAt())
      .updatedAt(product.getUpdatedAt())
      .build();
  }

}
