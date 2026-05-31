package com.universidad.proyecto.findtrack.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.universidad.proyecto.findtrack.dto.response.AuthResponseDTO;
import com.universidad.proyecto.findtrack.exceptions.EmailAlreadyExistsException;
import com.universidad.proyecto.findtrack.exceptions.InvalidCredentialsException;
import com.universidad.proyecto.findtrack.dto.request.LogInRequestDTO;
import com.universidad.proyecto.findtrack.dto.request.RegisterRequestDTO;

import com.universidad.proyecto.findtrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.universidad.proyecto.findtrack.model.User;

import com.universidad.proyecto.findtrack.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;

    
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    


    public AuthResponseDTO register(RegisterRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email ya registrado");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LogInRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponseDTO(token);
    }



}
