package com.zenveus.the_culinary_academy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private Long paymentId;
    private StudentProgramDto studentProgram;
    private String paymentDescription;
    private Double amount;
    private String paymentDate;
    private String paymentTime;
    private UserDto user;
    private StudentDto student;
}
