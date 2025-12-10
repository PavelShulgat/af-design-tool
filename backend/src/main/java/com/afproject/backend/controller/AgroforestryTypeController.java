package com.afproject.backend.controller;

import com.afproject.backend.model.dto.AgroforestryTypeDto;
import com.afproject.backend.service.AgroforestryTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agroforestry-types")
@CrossOrigin
public class AgroforestryTypeController {

    private final AgroforestryTypeService agroforestryTypeService;

    public AgroforestryTypeController(AgroforestryTypeService agroforestryTypeService) {
        this.agroforestryTypeService = agroforestryTypeService;
    }

    @GetMapping
    public List<AgroforestryTypeDto> getAllTypes() {
        return agroforestryTypeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public AgroforestryTypeDto getTypeById(@PathVariable Long id) {
        return agroforestryTypeService.getTypeById(id);
    }
}
