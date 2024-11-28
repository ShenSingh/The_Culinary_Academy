package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.StudentDAO;
import com.zenveus.the_culinary_academy.dto.StudentProgramDto;
import com.zenveus.the_culinary_academy.entity.Payment;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.StudentProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean add(Student entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Student entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        return false;
    }

    @Override
    public Object search(Student entity) throws Exception {
        return null;
    }

    @Override
    public List<Student> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Student> list = session.createQuery("from Student").list();
        transaction.commit();
        session.close();

        return list;
    }

    @Override
    public Student exist(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, id);
        transaction.commit();
        session.close();

        return student;
    }

    @Override
    public Long getLastId() {
        Long id = 0L;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Student> list = session.createQuery("from Student order by id desc").list();
        transaction.commit();
        session.close();
        if (list.size() > 0) {
            id = Long.valueOf(list.get(0).getStudentId());
        }
        return id;
    }


}
