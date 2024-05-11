package br.com.anunciabem.estoquefacil.domain.entities;

import br.com.anunciabem.estoquefacil.domain.constraints.ValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "\"user\"")
public class User implements Serializable, UserDetails {

  @Serial
  private static final long serialVersionUID = 4050401775381276820L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
  @SequenceGenerator(name = "seq_users", sequenceName = "seq_users", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  @NotBlank
  @Size(min = 6, max = 120)
  @Column(name = "name", length = 120, nullable = false)
  private String name;

  @Email
  @Size(max = 120)
  @Column(name = "email", length = 50, nullable = false, unique = true)
  private String email;

  @NotBlank
  @Size(max = 50)
  @Column(name = "username", length = 50, nullable = false, unique = true)
  private String username;

  @NotBlank
  @Size(max = 120)
  @Column(name = "password", length = 120, nullable = false)
  private String password;

  @NotNull
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Stream.of(this.role)
      .map(it -> (GrantedAuthority) () -> "ROLE_" + it.name())
      .collect(Collectors.toSet());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void changePassword(
    final String newPassword,
    final PasswordEncoder passwordEncoder
  ) {
    ValidationUtils.requireNonNull(newPassword, "Password cannot be null");
    if (passwordEncoder.matches(newPassword, this.password)) {
      throw new IllegalArgumentException("New password must be different from the old password");
    }
    this.password = passwordEncoder.encode(newPassword);
    this.updatedAt = Instant.now();
  }

  public void changeRole(final Role role) {
    ValidationUtils.requireNonNull(role, "Role cannot be null");
    if (this.role == role) {
      throw new IllegalArgumentException("New role must be different from the old role");
    }
    this.role = role;
    this.updatedAt = Instant.now();
  }

}
