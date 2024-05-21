package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.Brand;
import br.com.anunciabem.estoquefacil.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  boolean existsByName(String value);

  Optional<Brand> findByName(String name);

  default Brand findByIdOrElseThrow(final Long brandId) {
    return this.findById(brandId)
      .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
  }

  @Query(
    """
    SELECT b
    FROM Brand b
    WHERE
      (:brandId IS NULL OR b.id = :brandId)
      AND (:name IS NULL OR b.name = :name)
      AND (:description IS NULL OR b.description = :description)
      AND (:categoryId IS NULL OR b.category.id = :categoryId)
      AND (:categoryName IS NULL OR b.category.name = :categoryName)
      AND (:searchText IS NULL OR b.name LIKE %:searchText% OR b.description LIKE %:searchText% OR b.category.name LIKE %:searchText%)
    """
  )
  Page<Brand> findAll(
    Long brandId,
    String name,
    String description,
    Long categoryId,
    String categoryName,
    String searchText,
    Pageable pageable
  );

}
