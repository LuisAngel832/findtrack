package com.universidad.proyecto.findtrack.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.universidad.proyecto.findtrack.security.UserPrincipal;
import com.universidad.proyecto.findtrack.service.CategoryService;


import jakarta.validation.Valid;

import com.universidad.proyecto.findtrack.dto.request.CategoryRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.CategoryResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getUserCategories(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<CategoryResponseDTO> categories = categoryService.getUserCategories(userPrincipal.getId());
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CategoryRequestDTO categoryRequest) {
        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequest, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }

}
