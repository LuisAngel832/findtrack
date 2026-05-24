package com.universidad.proyecto.findtrack.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.universidad.proyecto.findtrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username)  throws UsernameNotFoundException{

        try{
            return userRepository.findById(UUID.fromString(username))
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }
        catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("Invalid UUID format");
        }
    }
}
