package com.zenveus.the_culinary_academy.dao.custom.impl;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Object search(String id) throws Exception {
        return null;
    }

    @Override
    public List<User> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from User").list();
        transaction.commit();
        session.close();

        return list;
    }

    @Override
    public Object exist(String id) throws Exception {  // id eka nh ne nisa
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        transaction.commit();

        return user;
    }
}
