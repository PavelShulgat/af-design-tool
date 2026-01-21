package com.afproject.backend.model.dto;

    public record ToolDetailsDto(
        Long id,
        String name,
        String description,
        String info,
        String typeName,
        String image_path
) {}

