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

  @Builder.Default
  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "brand")
  private Set<Model> models = new HashSet<>(0);

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

  public void addModel(final Model model) {
    ValidationUtils.requireNonNull(model, "Model cannot be null");
    if (this.models == null) {
      this.models = new HashSet<>(0);
    }
    this.models.add(model);
    if (model.getBrand() != this) return;
    model.addBrand(this);
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
    final Brand brand = (Brand) o;
    return id != null && Objects.equals(id, brand.id);
  }

}
