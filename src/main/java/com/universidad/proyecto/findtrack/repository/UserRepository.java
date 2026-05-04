package com.universidad.proyecto.findtrack.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universidad.proyecto.findtrack.model.User;



public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
