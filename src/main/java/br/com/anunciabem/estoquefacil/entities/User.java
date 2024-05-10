package br.com.anunciabem.estoquefacil.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "user")
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 4050401775381276820L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @Column(name = "id", nullable = false)
  private Long id;


  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  @Column(name = "name", length = 120, nullable = false)
  private String name;

  @Column(name = "email", length = 50, nullable = false, unique = true)
  private String email;

  @Column(name = "username", length = 50, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 50, nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

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
    final User user = (User) o;
    return this.id != null && Objects.equals(this.id, user.id);
  }

}
