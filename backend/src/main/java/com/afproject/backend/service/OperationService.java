package com.afproject.backend.service;

import com.afproject.backend.mapper.OperationMapper;
import com.afproject.backend.model.dto.OperationDto;
import com.afproject.backend.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    public OperationService(OperationRepository operationRepository,
                            OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    public List<OperationDto> getAllOperations() {
        return operationRepository.findAll().stream()
                .map(operationMapper::toDto)
                .toList();
    }

    public OperationDto getOperationById(Long id) {
        return operationRepository.findById(id)
                .map(operationMapper::toDto)
                .orElse(null);
    }
}
