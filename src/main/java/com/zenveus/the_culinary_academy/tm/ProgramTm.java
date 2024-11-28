package com.zenveus.the_culinary_academy.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramTm {
    String programId;
    String programName;
    String duration;
    String fee;
}
