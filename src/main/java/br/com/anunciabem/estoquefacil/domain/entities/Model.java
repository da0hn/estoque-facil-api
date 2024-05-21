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
@Entity(name = "Model")
@Table(name = "model")
public class Model extends Auditable implements Serializable {

  @Serial
  private static final long serialVersionUID = -7498710341003257572L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_models")
  @SequenceGenerator(name = "seq_models", sequenceName = "seq_models", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotBlank
  @Column(name = "name", unique = true, length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "brand_id", nullable = false)
  private Brand brand;

  public void addBrand(final Brand brand) {
    ValidationUtils.requireNonNull(brand, "Brand cannot be null");
    this.brand = brand;
    if (this.brand.getModels().contains(this)) return;
    this.brand.addModel(this);
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
