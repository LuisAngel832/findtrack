package com.universidad.proyecto.findtrack.dto.response;

import java.util.UUID;

import com.universidad.proyecto.findtrack.model.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponseDTO {
    
    private UUID id;
    private String name;
    private String icon;
    private CategoryType type;
    private boolean isDefault;
}
