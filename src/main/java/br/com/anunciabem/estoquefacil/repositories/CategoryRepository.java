package br.com.anunciabem.estoquefacil.repositories;

import br.com.anunciabem.estoquefacil.domain.entities.Category;
import br.com.anunciabem.estoquefacil.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  boolean existsByName(String name);

  Optional<Category> findByName(String name);

  default Category findByIdOrElseThrow(final Long categoryId) {
    return this.findById(categoryId)
      .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Query("""
         SELECT category
         FROM Category category
         WHERE
           (:id IS NULL OR category.id = :id) AND
           (:name IS NULL OR category.name LIKE %:name%) AND
           (:description IS NULL OR category.description LIKE %:description%) AND
           (:searchText IS NULL OR category.name LIKE %:searchText% OR category.description LIKE %:searchText%)
         """)
  Page<Category> findAll(Long id, String name, String description, String searchText, Pageable pageable);

}
