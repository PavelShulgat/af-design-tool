package com.afproject.backend.repository;

import com.afproject.backend.model.entity.SilvoPastoralCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SilvoPastoralCombinationRepository extends JpaRepository<SilvoPastoralCombination, Long> {
    List<SilvoPastoralCombination> findByAgroforestryType_IdAndTree_IdAndLivestock_Id(Long typeId, Long treeId, Long livestockId);
}