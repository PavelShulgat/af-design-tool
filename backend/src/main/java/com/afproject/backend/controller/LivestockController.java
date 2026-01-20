package com.afproject.backend.controller;

import com.afproject.backend.model.dto.LivestockDto;
import com.afproject.backend.service.LivestockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livestock")
@RequiredArgsConstructor
public class LivestockController {

    private final LivestockService service;

    @GetMapping
    public List<LivestockDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public LivestockDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
