package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.ChartBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentProgramTransDAO;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.User;

import java.util.List;

public class ChartBOIMPL implements ChartBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);
    StudentProgramTransDAO studentProgramTransDAO = (StudentProgramTransDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENTPROGRAMTRANS);
    @Override
    public int getProgramCount() {
        List<Program> allPrograms=null;
        try {
            allPrograms = programDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(allPrograms!=null){
            return allPrograms.size();
        }
        return 0;
    }

    @Override
    public int getStudentCount() {
        List<Student> allStudents=null;
        try {
            allStudents = studentDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(allStudents!=null){
            return allStudents.size();
        }
        return 0;
    }

    @Override
    public int getCoordinatorCount() {
        List<User> allUsers=null;
        try {
            allUsers = userDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // if job role is coordinator, count

        if(allUsers!=null){
            int count=0;
            for (User user : allUsers) {
                // Ignore the case
                if(user.getJobRole().equalsIgnoreCase("coordinator")){
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public List<Object[]> getStudentCourseCount() {
        List<Object[]> studentCourseCount=null;
        try {
            studentCourseCount = studentProgramTransDAO.getStudentCourseCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return studentCourseCount;
    }

    @Override
    public List<Object[]> getMonthlyTotalPayments() {
        List<Object[]> monthlyTotalPayments=null;

        try {
            monthlyTotalPayments = studentProgramTransDAO.getMonthlyTotalPayments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return monthlyTotalPayments;
    }

    @Override
    public List<Object[]> getProgramsCountByDuration() {
        List<Object[]> programsCountByDuration=null;

        try {
            programsCountByDuration = programDAO.getProgramsCountByDuration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return programsCountByDuration;
    }
}
