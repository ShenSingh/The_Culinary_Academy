package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.ProgramDTO;
import com.zenveus.the_culinary_academy.dto.UserDTO;

import java.util.List;

public interface ProgramBO extends SuperBO {
    boolean addProgram(ProgramDTO programDTO);
    List<ProgramDTO> getAllProgram();

    boolean updateProgram(ProgramDTO programDTO);

    boolean deleteProgram(String programId);

    UserDTO isProgramExist(ProgramDTO programDTO);

    UserDTO searchProgram(String programId);
}
