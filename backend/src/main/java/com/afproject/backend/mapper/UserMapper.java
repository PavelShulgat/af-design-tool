package com.afproject.backend.mapper;

import org.springframework.stereotype.Component;

import com.afproject.backend.model.dto.CreateUserRequest;
import com.afproject.backend.model.dto.UserDto;
import com.afproject.backend.model.entity.User;
import com.afproject.backend.model.enums.Role;

@Component
public class UserMapper {

    public UserDto toDto(User entity){
            if (entity == null) return null;
            return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.isActive()
            );
    }

    public User toEntity(CreateUserRequest req, String normalizedEmail, String passwordHash){
            return User.builder()
                .email(normalizedEmail)
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .passwordHash(passwordHash)
                .role(Role.USER)
                .active(true)
                .build();
    }
    
}
