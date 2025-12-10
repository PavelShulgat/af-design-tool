package com.afproject.backend.controller;

import com.afproject.backend.model.dto.PlantDto;
import com.afproject.backend.service.PlantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public List<PlantDto> getAllPlants() {
        return plantService.getAllPlants();
    }

    @GetMapping("/{id}")
    public PlantDto getPlant(@PathVariable Long id) {
        return plantService.getPlantById(id);
    }
}
