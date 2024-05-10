package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.User;
import br.com.anunciabem.estoquefacil.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  default User findByUsernameOrElseThrow(final String username) {
    return this.findByUsername(username)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for username " + username));
  }

  default User findByIdOrElseThrow(final Long id) {
    return this.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for id " + id));
  }

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

}
