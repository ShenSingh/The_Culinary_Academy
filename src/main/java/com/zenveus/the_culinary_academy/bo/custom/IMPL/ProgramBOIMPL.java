package com.zenveus.the_culinary_academy.bo.custom.IMPL;

import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.ProgramDTO;
import com.zenveus.the_culinary_academy.dto.UserDTO;
import com.zenveus.the_culinary_academy.entity.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOIMPL implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public boolean addProgram(ProgramDTO programDTO) {
        try {
            // Convert ProgramDTO to Program entity
            Program program = new Program();
            program.setProgramId(programDTO.getProgramId());
            program.setProgramName(programDTO.getProgramName());
            program.setDuration(programDTO.getDuration());
            program.setFee(programDTO.getFee());

            // Add the Program entity to the database
            programDAO.add(program);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error adding program", e);
        }
    }

    @Override
    public List<ProgramDTO> getAllProgram() {
        List<ProgramDTO> programList = new ArrayList<>();
        try {
            List<Program> allPrograms = programDAO.getAll();

            // Convert each Program entity to ProgramDTO
            for (Program program : allPrograms) {
                programList.add(new ProgramDTO(
                        program.getProgramId(),
                        program.getProgramName(),
                        program.getDuration(),
                        program.getFee()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all programs", e);
        }
        return programList;
    }

    @Override
    public boolean updateProgram(ProgramDTO programDTO) {
        try {
            // Convert ProgramDTO to Program entity
            Program program = new Program();
            program.setProgramId(programDTO.getProgramId());
            program.setProgramName(programDTO.getProgramName());
            program.setDuration(programDTO.getDuration());
            program.setFee(programDTO.getFee());

            // Update the Program entity in the database
            programDAO.update(program);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error updating program", e);
        }
    }

    @Override
    public boolean deleteProgram(String programId) {
        try {
            // Delete the Program entity from the database
            programDAO.delete(programId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting program", e);
        }
    }

    @Override
    public UserDTO isProgramExist(ProgramDTO programDTO) {
        try {
            // Check if the program exists by its ID
            Program program = (Program) programDAO.exist(programDTO.getProgramId());
            if (program != null) {
                return new UserDTO();  // return UserDTO if program exists, you can modify as per the relation
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking if program exists", e);
        }
        return null;
    }

    @Override
    public UserDTO searchProgram(String programId) {
        try {
            // Search for a program by its ID
            Program program = (Program) programDAO.search(programId);
            if (program != null) {
                return new UserDTO();  // You can modify this return as needed
            }
        } catch (Exception e) {
            throw new RuntimeException("Error searching for program", e);
        }
        return null;
    }
}
