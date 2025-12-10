package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.PlantDto;
import com.afproject.backend.model.entity.Plant;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper {

    public PlantDto toDto(Plant entity) {
        if (entity == null) return null;
        return new PlantDto(
                entity.getId(),
                entity.getName(),
                entity.getNotes()
        );
    }
}
