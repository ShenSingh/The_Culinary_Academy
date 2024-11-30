package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.StudentProgramTransDAO;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentProgramTransDAOImpl implements StudentProgramTransDAO {
    @Override
    public boolean addStudentAndPrograms(Student student, List<StudentProgram> studentProgram, Payment payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        for (StudentProgram dto : studentProgram) {
            session.persist(dto);
        }
        session.save(payment);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean updateStudentAndPrograms(Student student, List<StudentProgram> studentPrograms, Payment payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Check if the student program exists already by studentId and programId
        for (StudentProgram studentProgram : studentPrograms) {
            StudentProgram existingStudentProgram = (StudentProgram) session.createQuery("from StudentProgram sp where sp.student.studentId = :studentId and sp.program.programId = :programId")
                    .setParameter("studentId", student.getStudentId())
                    .setParameter("programId", studentProgram.getProgram().getProgramId())
                    .uniqueResult();
            if (existingStudentProgram != null) {
                session.save(payment);
                session.merge(studentProgram);
            } else {
                // Save the new student program
                session.save(payment);
                session.save(studentProgram);
            }
        }

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<Object[]> getStudentCourseCount() {
        List<Object[]> studentCourseCount = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        studentCourseCount = session.createQuery("SELECT p.programId, COUNT(sp.student.studentId) FROM StudentProgram sp JOIN sp.program p GROUP BY p.programId", Object[].class)
                .list();
        transaction.commit();
        session.close();


        return studentCourseCount;
    }

    @Override
    public List<Object[]> getStudentsDoingAllPrograms() {
        List<Object[]> studentsDoingAllPrograms = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        studentsDoingAllPrograms = session.createQuery("SELECT s.studentId FROM Student s WHERE (SELECT COUNT(sp.program.programId) FROM StudentProgram sp WHERE sp.student.studentId = s.studentId) = (SELECT COUNT(p.programId) FROM Program p)", Object[].class)
                .list();
        transaction.commit();
        session.close();

        return studentsDoingAllPrograms;
    }

    @Override
    public List<Object[]> getMonthlyTotalPayments() {
        List<Object[]> monthlyTotalPayments = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        monthlyTotalPayments = session.createQuery("SELECT MONTH(p.paymentDate), SUM(p.amount) FROM Payment p GROUP BY MONTH(p.paymentDate)", Object[].class)
                .list();
        transaction.commit();
        session.close();

        return monthlyTotalPayments;
    }

}
