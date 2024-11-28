package com.zenveus.the_culinary_academy.dao.custom;

import com.zenveus.the_culinary_academy.dao.CrudDAO;
import com.zenveus.the_culinary_academy.dto.StudentProgramDto;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    Long getLastId();

}
