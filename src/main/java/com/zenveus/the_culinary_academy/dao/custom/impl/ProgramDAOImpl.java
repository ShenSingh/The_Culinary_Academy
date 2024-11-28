package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.entity.Program;
import org.hibernate.Session;

import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {
    @Override
    public boolean add(Program entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Program entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Program entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public Object search(Program entity) throws Exception {
        return null;
    }

    @Override
    public List<Program> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        List<Program> programs = session.createQuery("from Program").list();
        session.getTransaction().commit();
        session.close();

        return programs;
    }

    @Override
    public Program exist(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        Program program = session.get(Program.class, id);
        session.getTransaction().commit();
        session.close();

        return program;
    }

    @Override
    public void delete(String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        Program program = session.get(Program.class, programId);
        session.delete(program);
        session.getTransaction().commit();
        session.close();
    }
}
