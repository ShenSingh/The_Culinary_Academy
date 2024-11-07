package com.zenveus.the_culinary_academy.dao.custom;

import com.zenveus.the_culinary_academy.dao.CrudDAO;
import com.zenveus.the_culinary_academy.entity.Program;

public interface ProgramDAO extends CrudDAO<Program> {
    void delete(String programId);
}
