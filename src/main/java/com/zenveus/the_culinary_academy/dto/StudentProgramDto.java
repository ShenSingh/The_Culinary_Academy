package com.zenveus.the_culinary_academy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentProgramDto {
    private Long studentProgramId;
    private StudentDto student;
    private ProgramDto program;
    private String payOption;
    private double installmentFee;
    private double totalDue;
    private String payStatus;
}
