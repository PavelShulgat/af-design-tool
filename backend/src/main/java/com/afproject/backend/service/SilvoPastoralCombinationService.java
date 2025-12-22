package com.afproject.backend.service;


import com.afproject.backend.mapper.SilvoPastoralCombinationMapper;
import com.afproject.backend.model.dto.CreateSilvoPastoralCombinationRequest;
import com.afproject.backend.model.dto.SilvoPastoralCombinationDto;
import com.afproject.backend.model.entity.*;
import com.afproject.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SilvoPastoralCombinationService {

    private final SilvoPastoralCombinationRepository repo;
    private final AgroforestryTypeRepository typeRepo;
    private final TreeRepository treeRepo;
    private final LivestockRepository livestockRepo;
    private final OperationRepository operationRepo;
    private final FarmingTechnologyRepository techRepo;
    private final SilvoPastoralCombinationMapper mapper;

    public SilvoPastoralCombinationService(
            SilvoPastoralCombinationRepository repo,
            AgroforestryTypeRepository typeRepo,
            TreeRepository treeRepo,
            LivestockRepository livestockRepo,
            OperationRepository operationRepo,
            FarmingTechnologyRepository techRepo,
            SilvoPastoralCombinationMapper mapper
    ) {
        this.repo = repo;
        this.typeRepo = typeRepo;
        this.treeRepo = treeRepo;
        this.livestockRepo = livestockRepo;
        this.operationRepo = operationRepo;
        this.techRepo = techRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<SilvoPastoralCombinationDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public SilvoPastoralCombinationDto getById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("SilvoPastoralCombination not found: " + id)));
    }

    public SilvoPastoralCombinationDto create(CreateSilvoPastoralCombinationRequest req) {
        AgroforestryType type = typeRepo.findById(req.agroforestryTypeId())
                .orElseThrow(() -> new EntityNotFoundException("AgroforestryType not found: " + req.agroforestryTypeId()));
        Tree tree = treeRepo.findById(req.treeId())
                .orElseThrow(() -> new EntityNotFoundException("Tree not found: " + req.treeId()));
        Livestock livestock = livestockRepo.findById(req.livestockId())
                .orElseThrow(() -> new EntityNotFoundException("Livestock not found: " + req.livestockId()));
        Operation operation = operationRepo.findById(req.operationId())
                .orElseThrow(() -> new EntityNotFoundException("Operation not found: " + req.operationId()));
        FarmingTechnology tech = techRepo.findById(req.farmingTechnologyId())
                .orElseThrow(() -> new EntityNotFoundException("FarmingTechnology not found: " + req.farmingTechnologyId()));

        SilvoPastoralCombination saved = repo.save(SilvoPastoralCombination.builder()
                .agroforestryType(type)
                .tree(tree)
                .livestock(livestock)
                .operation(operation)
                .farmingTechnology(tech)
                .notes(req.notes())
                .build());

        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SilvoPastoralCombination not found: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SilvoPastoralCombinationDto> findForAdvisor(Long typeId, Long treeId, Long livestockId) {
        return repo.findByAgroforestryType_IdAndTree_IdAndLivestock_Id(typeId, treeId, livestockId)
                .stream().map(mapper::toDto).toList();
    }
}