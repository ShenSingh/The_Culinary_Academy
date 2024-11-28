package com.zenveus.the_culinary_academy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramDto {
    private String programId;
    private String programName;
    private String duration;
    private Double fee;
}
