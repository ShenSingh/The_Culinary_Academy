package com.zenveus.the_culinary_academy.dao;

import com.zenveus.the_culinary_academy.dao.custom.impl.ProgramDAOImpl;
import com.zenveus.the_culinary_academy.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDAOFactory(){
        return(daoFactory==null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        USER, PROGRAM
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case USER:
                return new UserDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            default:
                return null;
        }
    }
}
