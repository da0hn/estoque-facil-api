package br.com.anunciabem.estoquefacil.domain.entities;

import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Product")
@Table(name = "products")
public class Product extends Auditable implements Serializable {

  @Serial
  private static final long serialVersionUID = -8455989060491224747L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_products")
  @SequenceGenerator(name = "seq_products", sequenceName = "seq_products", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", unique = true, length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal costPrice;

  @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal salePrice;

  @Column(name = "imei", nullable = false)
  private String imei;

  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  public void addBrand(final Model model) {
    ValidationUtils.requireNonNull(model, "Model cannot be null");
    this.model = model;
    if (this.model.getProducts().contains(this)) return;
    this.model.addProduct(this);
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
    final Product product = (Product) o;
    return this.id != null && Objects.equals(this.id, product.id);
  }

}
