package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.AgroforestryTypeDto;
import com.afproject.backend.model.entity.AgroforestryType;
import org.springframework.stereotype.Component;

@Component
public class AgroforestryTypeMapper {

    public AgroforestryTypeDto toDto(AgroforestryType entity) {
        if (entity == null) return null;
        return new AgroforestryTypeDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}