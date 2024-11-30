package com.zenveus.the_culinary_academy.dao.custom;

import com.zenveus.the_culinary_academy.dao.CrudDAO;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;

import java.util.List;

public interface StudentProgramDAO extends CrudDAO<StudentProgram> {

    List<StudentProgram> getProgramsForStudent(String studentId);

    StudentProgram exist(String programDetail, String studentId);

    List<Object[]> getStudentsByProgram(String selectedProgram);
}
