package com.afproject.backend.controller;

import com.afproject.backend.model.dto.CreateSilvoPastoralCombinationRequest;
import com.afproject.backend.model.dto.SilvoPastoralCombinationDto;
import com.afproject.backend.service.SilvoPastoralCombinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/silvo-pastoral-combinations")
public class SilvoPastoralCombinationController {

    private final SilvoPastoralCombinationService service;

    public SilvoPastoralCombinationController(SilvoPastoralCombinationService service) {
        this.service = service;
    }

    @GetMapping
    public List<SilvoPastoralCombinationDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SilvoPastoralCombinationDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SilvoPastoralCombinationDto create(@Valid @RequestBody CreateSilvoPastoralCombinationRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/advisor")
    public List<SilvoPastoralCombinationDto> advisor(
            @RequestParam Long agroforestryTypeId,
            @RequestParam Long treeId,
            @RequestParam Long livestockId
    ) {
        return service.findForAdvisor(agroforestryTypeId, treeId, livestockId);
    }
}