package com.zenveus.the_culinary_academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "program")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "fee", nullable = false)
    private Double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private Set<StudentProgram> studentPrograms = new HashSet<>();

}