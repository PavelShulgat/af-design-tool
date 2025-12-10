package com.afproject.backend.controller;

import com.afproject.backend.model.dto.ToolDto;
import com.afproject.backend.service.ToolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public List<ToolDto> getAllTools() {
        return toolService.getAllTools();
    }

    @GetMapping("/{id}")
    public ToolDto getTool(@PathVariable Long id) {
        return toolService.getToolById(id);
    }
}
