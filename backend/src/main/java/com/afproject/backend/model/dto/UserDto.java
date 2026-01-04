package com.afproject.backend.model.dto;

import java.util.UUID;
import com.afproject.backend.model.enums.Role;

public record UserDto (
    UUID id,
    String email,
    String firstName,
    String lastName,
    String passwordHash,
    Role role,
    boolean active
){}
