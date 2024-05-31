package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.Product;
import br.com.anunciabem.estoquefacil.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

  boolean existsByName(String name);

  Optional<Product> findByName(String name);

  default Product findByIdOrElseThrow(final Long productId) {
    return this.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
  }

  @Query("""
           SELECT p
           FROM Product p
           WHERE
             (:productId IS NULL OR p.id = :productId)
             AND (:name IS NULL OR p.name = :name)
             AND (:description IS NULL OR p.description = :description)
             AND (:modelId IS NULL OR p.model.id = :modelId)
             AND (:modelName IS NULL OR p.model.name = :modelName)
             AND (:searchText IS NULL OR p.name LIKE %:searchText% OR p.description LIKE %:searchText%)
         """)
  Page<Product> findAll(
    Long productId,
    String name,
    String description,
    Long modelId,
    String modelName,
    String searchText,
    Pageable pageable
  );

}
