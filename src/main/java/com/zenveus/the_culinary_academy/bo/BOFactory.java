package com.zenveus.the_culinary_academy.bo;

import com.zenveus.the_culinary_academy.bo.custom.StudentBO;
import com.zenveus.the_culinary_academy.bo.custom.impl.ChartBOIMPL;
import com.zenveus.the_culinary_academy.bo.custom.impl.ProgramBOIMPL;
import com.zenveus.the_culinary_academy.bo.custom.impl.StudentBOIMPL;
import com.zenveus.the_culinary_academy.bo.custom.impl.UserBOIMPL;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        PROGRAM, USER, STUDENT, CHART
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case USER:
                return new UserBOIMPL();
            case PROGRAM:
                return new ProgramBOIMPL();
            case STUDENT:
                return new StudentBOIMPL();
            case CHART:
                return new ChartBOIMPL();
            default:
                return null;
        }
    }
}
