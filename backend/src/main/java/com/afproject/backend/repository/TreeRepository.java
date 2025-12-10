package com.afproject.backend.repository;

import com.afproject.backend.model.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}

