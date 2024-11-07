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
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error adding program", e);
        }
        return true;
    }

    @Override
    public boolean delete(Program entity) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error deleting program", e);
        }
        return true;
    }

    @Override
    public boolean update(Program entity) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error updating program", e);
        }
        return true;
    }

    @Override
    public Program search(String id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.get(Program.class, id);  // Fetches the program by its ID
        } catch (Exception e) {
            throw new Exception("Error fetching program with ID: " + id, e);
        }
    }

    @Override
    public List<Program> getAll() throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<Program> query = session.createQuery("from Program", Program.class); // HQL query to get all programs
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error fetching all programs", e);
        }
    }

    @Override
    public Object exist(String id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Program program = session.get(Program.class, id);  // Checks if the program exists by its ID
            return program != null;
        } catch (Exception e) {
            throw new Exception("Error checking existence of program with ID: " + id, e);
        }
    }

    @Override
    public void delete(String programId) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            session.getTransaction().begin();
            Program program = session.get(Program.class, programId);
            if (program != null) {
                session.delete(program);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting program with ID: " + programId, e);
        }
    }
}
