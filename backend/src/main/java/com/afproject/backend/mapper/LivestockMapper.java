package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.LivestockDto;
import com.afproject.backend.model.entity.Livestock;
import org.springframework.stereotype.Component;

@Component
public class LivestockMapper {

    public LivestockDto toDto(Livestock e) {
        if (e == null) return null;
        return new LivestockDto(e.getId(), e.getName(), e.getDescription());
    }
}
