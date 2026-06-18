package com.universidad.proyecto.findtrack.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.universidad.proyecto.findtrack.dto.request.CategoryRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.CategoryResponseDTO;
import com.universidad.proyecto.findtrack.exceptions.CategoryInUseException;
import com.universidad.proyecto.findtrack.exceptions.ResourceNotFoundException;
import com.universidad.proyecto.findtrack.model.Category;
import com.universidad.proyecto.findtrack.model.CategoryType;
import com.universidad.proyecto.findtrack.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> getUserCategories(UUID userId) {

        return categoryRepository.findByUserIdOrUserIdIsNull(userId).stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName(),
                        category.getIcon(),
                        category.getType(),
                        category.isDefault()))
                .toList();
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequest, UUID userId) {
        Category category = Category.builder()
                .userId(userId)
                .name(categoryRequest.getName())
                .icon(categoryRequest.getIcon())
                .type(CategoryType.valueOf(categoryRequest.getType()))
                .isDefault(false)
                .build();

        category = categoryRepository.save(category);

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getIcon(),
                category.getType(),
                category.isDefault());
    }

    public void deleteCategory(UUID categoryId, UUID userId) {
        Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if(category.isDefault()) {
            throw new AccessDeniedException("No se puede eliminar una categoría predeterminada");
        }

        if(!category.getUserId().equals(userId)) {
            throw new AccessDeniedException("No se puede eliminar una categoría que no pertenece al usuario");
        }

        if(categoryRepository.existsTransactionsByCategoryId(categoryId)) {
            throw new CategoryInUseException("No se puede eliminar la categoría porque está en uso por una transacción");
        }
        categoryRepository.delete(category);
    }


}
