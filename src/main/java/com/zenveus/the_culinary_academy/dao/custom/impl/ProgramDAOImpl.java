package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.entity.Program;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

        try {
            // Fetch the Program entity
            Program program = session.get(Program.class, programId);

            if (program != null) {
                // Nullify the program references in StudentProgram
                Query query = session.createQuery("UPDATE StudentProgram sp SET sp.program = NULL WHERE sp.program.programId = :programId");
                query.setParameter("programId", programId);
                query.executeUpdate();

                // Now delete the program
                session.delete(program);
            } else {
                System.out.println("Program not found with ID: " + programId);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Object[]> getProgramsCountByDuration() {
        List<Object[]> programsCountByDuration = null;

        Session session = FactoryConfiguration.getInstance().getSession();
        session.getTransaction().begin();
        programsCountByDuration = session.createQuery("SELECT p.duration, COUNT(p.programId) FROM Program p GROUP BY p.duration", Object[].class)
                .list();
        session.getTransaction().commit();
        session.close();

        return programsCountByDuration;
    }

}
