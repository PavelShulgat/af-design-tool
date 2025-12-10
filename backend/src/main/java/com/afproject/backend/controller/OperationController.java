package com.afproject.backend.controller;

import com.afproject.backend.model.dto.OperationDto;
import com.afproject.backend.service.OperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@CrossOrigin
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public List<OperationDto> getAllOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/{id}")
    public OperationDto getOperation(@PathVariable Long id) {
        return operationService.getOperationById(id);
    }
}
