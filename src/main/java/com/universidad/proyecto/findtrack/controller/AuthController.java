package com.universidad.proyecto.findtrack.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import com.universidad.proyecto.findtrack.service.AutnService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.universidad.proyecto.findtrack.dto.request.LogInRequestDTO;
import com.universidad.proyecto.findtrack.dto.request.RegisterRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.AuthResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutnService autnService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthResponseDTO response = autnService.register(request);
        return ResponseEntity.created(null).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LogInRequestDTO request) {
        AuthResponseDTO response = autnService.login(request);
        return ResponseEntity.ok(response);
    }

}
