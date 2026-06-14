package com.universidad.proyecto.findtrack.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.universidad.proyecto.findtrack.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
    List<Category> findByUserIdOrUserIdIsNull(UUID userId);
}
