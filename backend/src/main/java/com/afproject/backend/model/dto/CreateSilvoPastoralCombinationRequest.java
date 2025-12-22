package com.afproject.backend.model.dto;

import jakarta.validation.constraints.NotNull;

public record CreateSilvoPastoralCombinationRequest(
        @NotNull Long agroforestryTypeId,
        @NotNull Long treeId,
        @NotNull Long livestockId,
        @NotNull Long operationId,
        @NotNull Long farmingTechnologyId,
        String notes
) {}
