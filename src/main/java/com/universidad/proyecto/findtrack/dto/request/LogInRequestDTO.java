package com.universidad.proyecto.findtrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequestDTO {

    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email no es válido")
    private String email;
    @NotBlank(message = "Password es obligatorio")
    private String password;

}
