package com.universidad.proyecto.findtrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    
    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotBlank(message = "Email es requerido")
    @Email(message = "Email no es válido")
    private String email;

    @NotBlank(message = "Contraseña es requerida")
    private String password;
}
