package com.afproject.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "silvo_arable_combinations",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_silvo_arable_combo",
                columnNames = {"agroforestry_type_id", "tree_id", "plant_id", "operation_id", "farming_technology_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SilvoArableCombination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agroforestry_type_id", nullable = false)
    private AgroforestryType agroforestryType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farming_technology_id", nullable = false)
    private FarmingTechnology farmingTechnology;

    @Column
    private String notes;
}