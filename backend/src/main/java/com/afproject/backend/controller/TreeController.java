package com.afproject.backend.controller;

import com.afproject.backend.model.dto.TreeDto;
import com.afproject.backend.service.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public List<TreeDto> getAllTrees() {
        return treeService.getAllTrees();
    }

    @GetMapping("/{id}")
    public TreeDto getTree(@PathVariable Long id) {
        return treeService.getTreeById(id);
    }
}
