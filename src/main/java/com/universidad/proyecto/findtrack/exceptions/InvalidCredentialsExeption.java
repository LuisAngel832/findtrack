package com.universidad.proyecto.findtrack.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsExeption extends RuntimeException {
    public InvalidCredentialsExeption(String message) {
        super(message);
    }
    
}
