package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.ToolDto;
import com.afproject.backend.model.entity.Tool;
import org.springframework.stereotype.Component;

@Component
public class ToolMapper {

    public ToolDto toDto(Tool entity) {
        if (entity == null) return null;
        return new ToolDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
