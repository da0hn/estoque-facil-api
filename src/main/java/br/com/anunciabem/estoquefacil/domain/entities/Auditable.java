package br.com.anunciabem.estoquefacil.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable  {

  @NotNull
  @CreatedDate
  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;

  @NotNull
  @LastModifiedDate
  @Column(name = "updated_at", updatable = false)
  private Instant updatedAt;

  @NotNull
  @CreatedBy
  @Column(name = "created_by", nullable = false)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "modified_by")
  private String modifiedBy;

}
