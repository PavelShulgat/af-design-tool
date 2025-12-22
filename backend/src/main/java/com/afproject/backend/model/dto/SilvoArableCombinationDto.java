package com.afproject.backend.model.dto;

public record SilvoArableCombinationDto(
        Long id,
        Long agroforestryTypeId,
        String agroforestryTypeName,
        Long treeId,
        String treeName,
        Long plantId,
        String plantName,
        Long operationId,
        String operationName,
        Long farmingTechnologyId,
        String farmingTechnologyName,
        String notes
) {}