package com.universidad.proyecto.findtrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String name;

    private String icon;

    @Pattern(regexp = "^(INCOME|EXPENSE)$", message = "El tipo debe ser INCOME o EXPENSE")
    private String type;
}