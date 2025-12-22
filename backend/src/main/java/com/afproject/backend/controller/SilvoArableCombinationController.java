package com.afproject.backend.controller;

import com.afproject.backend.model.dto.CreateSilvoArableCombinationRequest;
import com.afproject.backend.model.dto.SilvoArableCombinationDto;
import com.afproject.backend.service.SilvoArableCombinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/silvo-arable-combinations")
public class SilvoArableCombinationController {

    private final SilvoArableCombinationService service;

    public SilvoArableCombinationController(SilvoArableCombinationService service) {
        this.service = service;
    }

    @GetMapping
    public List<SilvoArableCombinationDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SilvoArableCombinationDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SilvoArableCombinationDto create(@Valid @RequestBody CreateSilvoArableCombinationRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Advisor endpoint (frontend-friendly)
    @GetMapping("/advisor")
    public List<SilvoArableCombinationDto> advisor(
            @RequestParam Long agroforestryTypeId,
            @RequestParam Long treeId,
            @RequestParam Long plantId
    ) {
        return service.findForAdvisor(agroforestryTypeId, treeId, plantId);
    }
}