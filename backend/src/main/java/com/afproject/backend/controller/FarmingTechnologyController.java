package com.afproject.backend.controller;

import com.afproject.backend.model.dto.ToolDetailsDto;
import com.afproject.backend.model.dto.ToolDto;
import com.afproject.backend.service.FarmingTechnologyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin
public class FarmingTechnologyController {

    private final FarmingTechnologyService toolService;

    public FarmingTechnologyController(FarmingTechnologyService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public List<ToolDto> getAllTools() {
        return toolService.getAllTools();
    }

    @GetMapping("/{id}")
    public ToolDetailsDto getById(@PathVariable Long id) {
        return toolService.getToolDetails(id);
    }
}
