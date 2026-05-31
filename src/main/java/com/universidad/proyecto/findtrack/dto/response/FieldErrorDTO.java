package com.universidad.proyecto.findtrack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDTO {
    private String field;
    private String message;
}
