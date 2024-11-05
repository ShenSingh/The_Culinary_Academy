package com.zenveus.the_culinary_academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_nic", nullable = false)
    private String studentNic;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String adress;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "remaining_balance", nullable = false)
    private Double remainingBalance;

    @Column(name = "registration_time")
    private LocalTime registrationTime;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentProgram> studentPrograms = new HashSet<>();

}

