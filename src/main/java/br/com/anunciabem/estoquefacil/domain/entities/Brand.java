package br.com.anunciabem.estoquefacil.domain.entities;

import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Brand")
@Table(name = "brand")
public class Brand extends Auditable implements Serializable {

  @Serial
  private static final long serialVersionUID = -7498710341003257572L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_brands")
  @SequenceGenerator(name = "seq_brands", sequenceName = "seq_brands", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotBlank
  @Column(name = "name", unique = true, length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public void addCategory(final Category category) {
    ValidationUtils.requireNonNull(category, "Category cannot be null");
    this.category = category;
    if (this.category.getBrands().contains(this)) return;
    this.category.addBrand(this);
  }

  public void changeName(final String name) {
    if (!StringUtils.hasText(name)) {
      throw new BusinessValidationException("Brand name cannot be null or empty");
    }
    this.name = name;
  }

  public void changeDescription(final String description) {
    if (description != null && description.isBlank()) {
      throw new BusinessValidationException("Brand description cannot be empty");
    }
    this.description = description;
  }

}
