package com.zenveus.the_culinary_academy.dao.custom;

import com.zenveus.the_culinary_academy.dao.SuperDAO;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;

import java.util.List;

public interface StudentProgramTransDAO extends SuperDAO {
    boolean addStudentAndPrograms(Student student, List<StudentProgram> studentProgram, Payment payment);

    boolean updateStudentAndPrograms(Student student, List<StudentProgram> studentPrograms, Payment payment);

    List<Object[]> getStudentCourseCount();

    List<Object[]> getStudentsDoingAllPrograms();

    List<Object[]> getMonthlyTotalPayments();
}
