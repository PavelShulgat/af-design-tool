package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.SilvoArableCombinationDto;
import com.afproject.backend.model.entity.SilvoArableCombination;
import org.springframework.stereotype.Component;

@Component
public class SilvoArableCombinationMapper {

    public SilvoArableCombinationDto toDto(SilvoArableCombination e) {
        return new SilvoArableCombinationDto(
                e.getId(),
                e.getAgroforestryType().getId(),
                e.getAgroforestryType().getName(),
                e.getTree().getId(),
                e.getTree().getName(),
                e.getPlant().getId(),
                e.getPlant().getName(),
                e.getOperation().getId(),
                e.getOperation().getName(),
                e.getFarmingTechnology().getId(),
                e.getFarmingTechnology().getName(),
                e.getNotes()
        );
    }
}
