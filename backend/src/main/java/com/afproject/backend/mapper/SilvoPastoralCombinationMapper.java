package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.SilvoPastoralCombinationDto;
import com.afproject.backend.model.entity.SilvoPastoralCombination;
import org.springframework.stereotype.Component;

@Component
public class SilvoPastoralCombinationMapper {

    public SilvoPastoralCombinationDto toDto(SilvoPastoralCombination e) {
        return new SilvoPastoralCombinationDto(
                e.getId(),
                e.getAgroforestryType().getId(),
                e.getAgroforestryType().getName(),
                e.getTree().getId(),
                e.getTree().getName(),
                e.getLivestock().getId(),
                e.getLivestock().getName(),
                e.getOperation().getId(),
                e.getOperation().getName(),
                e.getFarmingTechnology().getId(),
                e.getFarmingTechnology().getName(),
                e.getNotes()
        );
    }
}
