package com.afproject.backend.service;

import com.afproject.backend.mapper.PlantMapper;
import com.afproject.backend.model.dto.PlantDto;
import com.afproject.backend.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    public PlantService(PlantRepository plantRepository, PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

    public List<PlantDto> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(plantMapper::toDto)
                .toList();
    }

    public PlantDto getPlantById(Long id) {
        return plantRepository.findById(id)
                .map(plantMapper::toDto)
                .orElse(null); 
    }
}
