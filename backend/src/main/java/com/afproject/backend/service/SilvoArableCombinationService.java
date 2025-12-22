package com.afproject.backend.service;

import com.afproject.backend.mapper.SilvoArableCombinationMapper;
import com.afproject.backend.model.dto.CreateSilvoArableCombinationRequest;
import com.afproject.backend.model.dto.SilvoArableCombinationDto;
import com.afproject.backend.model.entity.*;
import com.afproject.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SilvoArableCombinationService {

    private final SilvoArableCombinationRepository repo;
    private final AgroforestryTypeRepository typeRepo;
    private final TreeRepository treeRepo;
    private final PlantRepository plantRepo;
    private final OperationRepository operationRepo;
    private final FarmingTechnologyRepository techRepo;
    private final SilvoArableCombinationMapper mapper;

    public SilvoArableCombinationService(
            SilvoArableCombinationRepository repo,
            AgroforestryTypeRepository typeRepo,
            TreeRepository treeRepo,
            PlantRepository plantRepo,
            OperationRepository operationRepo,
            FarmingTechnologyRepository techRepo,
            SilvoArableCombinationMapper mapper
    ) {
        this.repo = repo;
        this.typeRepo = typeRepo;
        this.treeRepo = treeRepo;
        this.plantRepo = plantRepo;
        this.operationRepo = operationRepo;
        this.techRepo = techRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<SilvoArableCombinationDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public SilvoArableCombinationDto getById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("SilvoArableCombination not found: " + id)));
    }

    public SilvoArableCombinationDto create(CreateSilvoArableCombinationRequest req) {
        AgroforestryType type = typeRepo.findById(req.agroforestryTypeId())
                .orElseThrow(() -> new EntityNotFoundException("AgroforestryType not found: " + req.agroforestryTypeId()));
        Tree tree = treeRepo.findById(req.treeId())
                .orElseThrow(() -> new EntityNotFoundException("Tree not found: " + req.treeId()));
        Plant plant = plantRepo.findById(req.plantId())
                .orElseThrow(() -> new EntityNotFoundException("Plant not found: " + req.plantId()));
        Operation operation = operationRepo.findById(req.operationId())
                .orElseThrow(() -> new EntityNotFoundException("Operation not found: " + req.operationId()));
        FarmingTechnology tech = techRepo.findById(req.farmingTechnologyId())
                .orElseThrow(() -> new EntityNotFoundException("FarmingTechnology not found: " + req.farmingTechnologyId()));

        SilvoArableCombination saved = repo.save(SilvoArableCombination.builder()
                .agroforestryType(type)
                .tree(tree)
                .plant(plant)
                .operation(operation)
                .farmingTechnology(tech)
                .notes(req.notes())
                .build());

        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SilvoArableCombination not found: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SilvoArableCombinationDto> findForAdvisor(Long typeId, Long treeId, Long plantId) {
        return repo.findByAgroforestryType_IdAndTree_IdAndPlant_Id(typeId, treeId, plantId)
                .stream().map(mapper::toDto).toList();
    }
}