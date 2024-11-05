package com.zenveus.the_culinary_academy.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {

        // Set Hibernate properties programmatically
        Properties hibernateProperties = new Properties();
        try {
            hibernateProperties.load(FactoryConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Configure Hibernate with properties and add annotated classes
        Configuration configuration = new Configuration()
                .addProperties(hibernateProperties)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Program.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Student.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.StudentProgram.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.User.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Payment.class);

        // Build the session factory
        sessionFactory = configuration.buildSessionFactory();
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

//package com.zenveus.the_culinary_academy.config;
//
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class FactoryConfiguration {
//    private static FactoryConfiguration factoryConfiguration;
//    private SessionFactory sessionFactory;
//
//    private FactoryConfiguration() {
//        Configuration configuration = new Configuration().configure()
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Program.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Student.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.StudentProgram.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.User.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Payment.class);
//        sessionFactory = configuration.configure().buildSessionFactory();
//    }
//
//    public static FactoryConfiguration getInstance() {
//        return (factoryConfiguration == null) ? factoryConfiguration =
//                new FactoryConfiguration() : factoryConfiguration;
//    }
//
//    public Session getSession() {
//        return sessionFactory.openSession();
//    }
//}