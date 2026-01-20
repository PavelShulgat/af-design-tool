package com.afproject.backend.service;

import com.afproject.backend.mapper.LivestockMapper;
import com.afproject.backend.model.dto.LivestockDto;
import com.afproject.backend.repository.LivestockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivestockService {

    private final LivestockRepository repo;
    private final LivestockMapper mapper;

    public List<LivestockDto> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public LivestockDto getById(Long id) {
        var e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livestock not found: " + id));
        return mapper.toDto(e);
    }
}
