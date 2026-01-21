package com.afproject.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "farming_technology")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmingTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farming_technology_type_id", nullable = false)
    private FarmingTechnologyType type;

    @Column
    private String image_path;
}
