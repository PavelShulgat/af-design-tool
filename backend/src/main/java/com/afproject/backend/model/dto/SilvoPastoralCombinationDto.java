package com.afproject.backend.model.dto;

public record SilvoPastoralCombinationDto(
        Long id,
        Long agroforestryTypeId,
        String agroforestryTypeName,
        Long treeId,
        String treeName,
        Long livestockId,
        String livestockName,
        Long operationId,
        String operationName,
        Long farmingTechnologyId,
        String farmingTechnologyName,
        String notes
) {}