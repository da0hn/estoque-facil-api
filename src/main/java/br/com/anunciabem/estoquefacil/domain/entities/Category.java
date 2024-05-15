package br.com.anunciabem.estoquefacil.domain.entities;

import br.com.anunciabem.estoquefacil.exceptions.BusinessValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Category")
@Table(name = "category")
public class Category extends Auditable implements Serializable {

  @Serial
  private static final long serialVersionUID = 8339463655592879879L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categories")
  @SequenceGenerator(name = "seq_categories", sequenceName = "seq_categories", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotBlank
  @Column(name = "name", unique = true, length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  public void changeName(final String name) {
    if (!StringUtils.hasText(name)) {
      throw new BusinessValidationException("Category name cannot be null or empty");
    }
    this.name = name;
  }

  public void changeDescription(final String description) {
    this.description = description;
  }

}
