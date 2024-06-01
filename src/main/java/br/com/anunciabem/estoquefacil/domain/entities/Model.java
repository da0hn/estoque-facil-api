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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Model")
@Table(name = "Model")
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

  @Builder.Default
  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "model", fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Product> products = new HashSet<>();

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

  public void addProduct(final Product product) {
    ValidationUtils.requireNonNull(product, "Product cannot be null");
    if (this.products == null) {
      this.products = new HashSet<>(0);
    }
    this.products.add(product);
    if (product.getModel() != this) return;
    product.addModel(this);
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
      this.getClass().hashCode();
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null) return false;
    final Class<?> oEffectiveClass = o instanceof HibernateProxy ?
      ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    final Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
      ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    final Model model = (Model) o;
    return this.id != null && Objects.equals(this.id, model.id);
  }

}
