package com.universidad.proyecto.findtrack.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import com.universidad.proyecto.findtrack.service.AuthService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.universidad.proyecto.findtrack.dto.request.LogInRequestDTO;
import com.universidad.proyecto.findtrack.dto.request.RegisterRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.AuthResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.UserResponseDTO;
import com.universidad.proyecto.findtrack.model.User;
import com.universidad.proyecto.findtrack.security.UserPrincipal;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService autnService;

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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        UserResponseDTO response = new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getCurrency()
        );
        return ResponseEntity.ok(response);
    }

}
