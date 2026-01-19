package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.ToolDto;
import com.afproject.backend.model.entity.FarmingTechnology;
import org.springframework.stereotype.Component;

@Component
public class FarmingTechnologyMapper {

    public ToolDto toDto(FarmingTechnology entity) {
        if (entity == null) return null;
        return new ToolDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getInfo()
        );
    }
}
