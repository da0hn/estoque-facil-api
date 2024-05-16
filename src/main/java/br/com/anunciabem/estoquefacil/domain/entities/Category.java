package br.com.anunciabem.estoquefacil.domain.entities;

import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import br.com.anunciabem.estoquefacil.domain.exceptions.BusinessValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

  @Builder.Default
  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "category")
  private Set<Brand> brands = new HashSet<>(0);

  public void addBrand(final Brand brand) {
    ValidationUtils.requireNonNull(brand, "Brand cannot be null");
    if (this.brands == null) {
      this.brands = new HashSet<>(0);
    }
    this.brands.add(brand);
    if (brand.getCategory() != this) return;
    brand.addCategory(this);
  }

  public void changeName(final String name) {
    if (!StringUtils.hasText(name)) {
      throw new BusinessValidationException("Category name cannot be null or empty");
    }
    this.name = name;
  }

  public void changeDescription(final String description) {
    this.description = description;
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
    final Category category = (Category) o;
    return this.id != null && Objects.equals(this.id, category.id);
  }

}
