package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;

import java.util.List;

public interface ProgramBO extends SuperBO {
    List<ProgramDto> getAllPrograms();

    boolean addProgram(ProgramDto programDto);

    boolean updateProgram(ProgramDto programDto);

    boolean deleteProgram(String programId);

    ProgramDto getProgramDetails(String programId);
}
