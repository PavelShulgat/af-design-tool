package com.afproject.backend.service;

import com.afproject.backend.model.dto.*;
import com.afproject.backend.model.entity.User;
import com.afproject.backend.model.enums.Role;
import com.afproject.backend.repository.UserRepository;
import com.afproject.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(CreateUserRequest req) {
        String email = normalize(req.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists");
        }

        User user = User.builder()
                .email(email)
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        String email = normalize(req.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, req.getPassword())
        );

        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(email))
                .tokenType("Bearer")
                .build();
    }

    private String normalize(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
