package com.universidad.proyecto.findtrack.dto.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponseDTO {

    private int status;

    private String error;

    private String message;

    private Instant timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErrorDTO> errors;

    public ExceptionResponseDTO(int status, String error, String message, Instant timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

}
