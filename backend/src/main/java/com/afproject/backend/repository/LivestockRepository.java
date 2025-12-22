package com.afproject.backend.repository;

import com.afproject.backend.model.entity.Livestock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivestockRepository extends JpaRepository<Livestock, Long> {
}