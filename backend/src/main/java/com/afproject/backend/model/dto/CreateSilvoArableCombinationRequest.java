package com.afproject.backend.model.dto;

import jakarta.validation.constraints.NotNull;

public record CreateSilvoArableCombinationRequest(
        @NotNull Long agroforestryTypeId,
        @NotNull Long treeId,
        @NotNull Long plantId,
        @NotNull Long operationId,
        @NotNull Long farmingTechnologyId,
        String notes
) {}