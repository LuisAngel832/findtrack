package com.universidad.proyecto.findtrack.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.universidad.proyecto.findtrack.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    List<Category> findByUserIdOrUserIdIsNull(UUID userId);

    @Query(value = "SELECT COUNT(*) > 0 FROM transactions WHERE category_id = :categoryId", nativeQuery = true)
    boolean existsTransactionsByCategoryId(@Param("categoryId") UUID categoryId);
}
