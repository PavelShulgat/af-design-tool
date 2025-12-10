package com.afproject.backend.service;

import com.afproject.backend.mapper.AgroforestryTypeMapper;
import com.afproject.backend.model.dto.AgroforestryTypeDto;
import com.afproject.backend.repository.AgroforestryTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgroforestryTypeService {

    private final AgroforestryTypeRepository agroforestryTypeRepository;
    private final AgroforestryTypeMapper agroforestryTypeMapper;

    public AgroforestryTypeService(AgroforestryTypeRepository agroforestryTypeRepository,
                                   AgroforestryTypeMapper agroforestryTypeMapper) {
        this.agroforestryTypeRepository = agroforestryTypeRepository;
        this.agroforestryTypeMapper = agroforestryTypeMapper;
    }

    public List<AgroforestryTypeDto> getAllTypes() {
        return agroforestryTypeRepository.findAll().stream()
                .map(agroforestryTypeMapper::toDto)
                .toList();
    }

    public AgroforestryTypeDto getTypeById(Long id) {
        return agroforestryTypeRepository.findById(id)
                .map(agroforestryTypeMapper::toDto)
                .orElse(null);
    }
}
