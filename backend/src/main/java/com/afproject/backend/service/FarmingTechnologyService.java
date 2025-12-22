package com.afproject.backend.service;

import com.afproject.backend.mapper.FarmingTechnologyMapper;
import com.afproject.backend.model.dto.ToolDto;
import com.afproject.backend.repository.FarmingTechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmingTechnologyService {

    private final FarmingTechnologyRepository toolRepository;
    private final FarmingTechnologyMapper toolMapper;

    public FarmingTechnologyService(FarmingTechnologyRepository toolRepository, FarmingTechnologyMapper toolMapper) {
        this.toolRepository = toolRepository;
        this.toolMapper = toolMapper;
    }

    public List<ToolDto> getAllTools() {
        return toolRepository.findAll().stream()
                .map(toolMapper::toDto)
                .toList();
    }

    public ToolDto getToolById(Long id) {
        return toolRepository.findById(id)
                .map(toolMapper::toDto)
                .orElse(null); // later you can throw 404
    }
}
