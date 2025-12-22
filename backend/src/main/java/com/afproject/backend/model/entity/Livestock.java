package com.afproject.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livestock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livestock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column
    private String description;
}