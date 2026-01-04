package com.afproject.backend.service;

import com.afproject.backend.mapper.UserMapper;
import com.afproject.backend.model.dto.CreateUserRequest;
import com.afproject.backend.model.dto.UserDto;
import com.afproject.backend.model.entity.User;
import com.afproject.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(CreateUserRequest req) {
        String email = normalizeEmail(req.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("User with this email already exists");
        }

        String hash = passwordEncoder.encode(req.getPassword());
        User saved = userRepository.save(userMapper.toEntity(req, email, hash));
        return userMapper.toDto(saved);
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    public UserDto getById(UUID id) {
    return userRepository.findById(id)
            .map(userMapper::toDto)
            .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
}
