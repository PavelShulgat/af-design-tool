package com.afproject.backend.repository;

import com.afproject.backend.model.entity.SilvoArableCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SilvoArableCombinationRepository extends JpaRepository<SilvoArableCombination, Long> {
    List<SilvoArableCombination> findByAgroforestryType_IdAndTree_IdAndPlant_Id(Long typeId, Long treeId, Long plantId);
}