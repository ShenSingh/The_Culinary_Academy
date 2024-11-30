package com.zenveus.the_culinary_academy.dao.custom;

import com.zenveus.the_culinary_academy.dao.CrudDAO;
import com.zenveus.the_culinary_academy.entity.Program;

import java.util.List;

public interface ProgramDAO extends CrudDAO<Program> {
    void delete(String programId);

    List<Object[]> getProgramsCountByDuration();
}
