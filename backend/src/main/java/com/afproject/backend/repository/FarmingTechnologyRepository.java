package com.afproject.backend.repository;

import com.afproject.backend.model.entity.FarmingTechnology;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FarmingTechnologyRepository extends JpaRepository<FarmingTechnology, Long> {
    @Query("""
        select t from FarmingTechnology t
        join fetch t.type
        where t.id = :id
    """)
    Optional<FarmingTechnology> findByIdWithType(@Param("id") Long id);
}
