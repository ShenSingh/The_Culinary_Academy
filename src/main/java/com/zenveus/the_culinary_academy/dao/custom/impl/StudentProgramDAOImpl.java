package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.EmployeeDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentProgramDAO;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentProgramDAOImpl implements StudentProgramDAO {

    @Override
    public boolean add(StudentProgram entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(StudentProgram entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(StudentProgram entity) throws Exception {
        return false;
    }

    @Override
    public Object search(StudentProgram entity) throws Exception {
        return null;
    }

    @Override
    public List<StudentProgram> getAll() throws Exception {
        return null;
    }

    @Override
    public StudentProgram exist(String id) throws Exception {
        return null;
    }

    @Override
    public List<StudentProgram> getProgramsForStudent(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        List<StudentProgram> studentPrograms = null;

        try {
            transaction = session.beginTransaction();
            // Use named parameters for clarity
            studentPrograms = session.createQuery("from StudentProgram sp where sp.student.studentId = :studentId", StudentProgram.class)
                    .setParameter("studentId", studentId)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error fetching programs for student ID: " + studentId, e);
        } finally {
            session.close();
        }

        return studentPrograms;
    }

    @Override
    public StudentProgram exist(String programDetail, String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("StudentProgramDAOImpl: exist: programDetail: " + programDetail);
        System.out.println("StudentProgramDAOImpl: exist: studentId: " + studentId);

        StudentProgram studentProgram = session.createQuery(
                        "from StudentProgram sp where sp.program.programId = :programDetail and sp.student.studentId = :studentId",
                        StudentProgram.class
                )
                .setParameter("programDetail", programDetail)
                .setParameter("studentId", studentId)
                .uniqueResult();


        transaction.commit();
        session.close();

        System.out.println("StudentProgramDAOImpl: exist: studentProgram: " + studentProgram);

        return studentProgram;
    }



}
