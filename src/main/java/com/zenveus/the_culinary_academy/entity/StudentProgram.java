package com.zenveus.the_culinary_academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "student_program")
public class StudentProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_program_id")
    private Long studentProgramId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(name = "pay_option")
    private String payOption;

    @Column(name = "installment_fee")
    private double installmentFee;

    @Column(name = "total_due")
    private double totalDue;

    @Column(name = "pay_status")
    private String payStatus;
}
