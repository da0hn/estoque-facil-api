package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.User;
import br.com.anunciabem.estoquefacil.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

  @Query(
    """
      SELECT u FROM User u
      WHERE
        (:name IS NULL OR u.name LIKE %:name%) AND
        (:username IS NULL OR u.username LIKE %:username%) AND
        (:email IS NULL OR u.email LIKE %:email%) AND
        (:searchText IS NULL OR u.name LIKE %:searchText% OR u.username LIKE %:searchText% OR u.email LIKE %:searchText%)
    """
  )
  Page<User> findAll(String name, String username, String email, String searchText, Pageable pageable);

}
