package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.Model;
import br.com.anunciabem.estoquefacil.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {

  boolean existsByName(String name);

  Optional<Model> findByName(String name);

  default Model findByIdOrElseThrow(final Long modelId) {
    return this.findById(modelId)
      .orElseThrow(() -> new ResourceNotFoundException("Model not found"));
  }

  @Query(
    """
    SELECT m
    FROM Model m
    WHERE
      (:modelId IS NULL OR m.id = :modelId)
      AND (:name IS NULL OR m.name = :name)
      AND (:description IS NULL OR m.description = :description)
      AND (:brandId IS NULL OR m.brand.id = :brandId)
      AND (:brandName IS NULL OR m.brand.name = :brandName)
      AND (:searchText IS NULL OR m.name LIKE %:searchText% OR m.description LIKE %:searchText% OR m.brand.name LIKE %:searchText%)
    """
  )
  Page<Model> findAll(
    Long modelId,
    String name,
    String description,
    Long brandId,
    String brandName,
    String searchText,
    Pageable pageable
  );

}
