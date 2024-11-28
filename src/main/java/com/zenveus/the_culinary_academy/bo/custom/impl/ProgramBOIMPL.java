package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.entity.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOIMPL implements ProgramBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public List<ProgramDto> getAllPrograms() {
        ArrayList<ProgramDto> programList = new ArrayList<>();
        List<Program> allPrograms = null;
        try {
            allPrograms = programDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Program program : allPrograms) {
            programList.add(new ProgramDto(program.getProgramId(), program.getProgramName(), program.getDuration(), program.getFee()));
        }

        return programList;
    }

    @Override
    public boolean addProgram(ProgramDto programDto) {
        try {
            programDAO.add(new Program(programDto.getProgramId(), programDto.getProgramName(), programDto.getDuration(), programDto.getFee()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean updateProgram(ProgramDto programDto) {
        try {
            programDAO.update(new Program(programDto.getProgramId(), programDto.getProgramName(), programDto.getDuration(), programDto.getFee()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteProgram(String programId) {
        try {
            programDAO.delete(programId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public ProgramDto getProgramDetails(String programId) {
        try {
            Program program = programDAO.exist(programId);
            return new ProgramDto(program.getProgramId(), program.getProgramName(), program.getDuration(), program.getFee());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
