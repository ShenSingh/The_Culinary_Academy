package com.zenveus.the_culinary_academy.config;

import com.zenveus.the_culinary_academy.entity.*;
import com.zenveus.the_culinary_academy.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Program.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(StudentProgram.class)
                .addAnnotatedClass(User.class);


        configuration.setProperties(properties);
        sessionFactory = configuration.buildSessionFactory();
        System.out.println("SessionFactory initialized successfully.");
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
