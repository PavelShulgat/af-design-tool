package com.afproject.backend.mapper;

import com.afproject.backend.model.dto.TreeDto;
import com.afproject.backend.model.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class TreeMapper {

    public TreeDto toDto(Tree entity) {
        if (entity == null) return null;
        return new TreeDto(
                entity.getId(),
                entity.getName(),
                entity.getNotes()
        );
    }
}

