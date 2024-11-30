package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.StudentDto;

import java.util.List;

public interface StudentBO extends SuperBO {
    void saveStudent(StudentDto studentDTO);

    void deleteStudent(String id);

    void updateStudent(StudentDto studentDTO);

    StudentDto searchStudentByStudentId(String id);

    List<StudentDto> getAllStudents();

    boolean saveStudentAndPrograms(StudentDto studentDto, String[][] programDetailsArray, String[][] paymentDetailsArray);

    List<String[]> getProgramsForStudent(String studentId);

    boolean updateStudentAndPrograms(StudentDto studentDto, List<String[]> programDetailsList, double total);

    List<Object[]> getStudentsByProgram(String selectedProgram);

    List<Object[]> getStudentsDoingAllPrograms();
}
