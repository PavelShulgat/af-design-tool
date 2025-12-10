package com.afproject.backend.service;

import com.afproject.backend.mapper.TreeMapper;
import com.afproject.backend.model.dto.TreeDto;
import com.afproject.backend.repository.TreeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;

    public TreeService(TreeRepository treeRepository, TreeMapper treeMapper) {
        this.treeRepository = treeRepository;
        this.treeMapper = treeMapper;
    }

    public List<TreeDto> getAllTrees() {
        return treeRepository.findAll().stream()
                .map(treeMapper::toDto)
                .toList();
    }

    public TreeDto getTreeById(Long id) {
        return treeRepository.findById(id)
                .map(treeMapper::toDto)
                .orElse(null); 
    }
}

