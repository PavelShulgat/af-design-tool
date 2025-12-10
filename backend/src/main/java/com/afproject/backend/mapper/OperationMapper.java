package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.OperationDto;
import com.afproject.backend.model.entity.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper {

    public OperationDto toDto(Operation entity) {
        if (entity == null) return null;
        return new OperationDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}