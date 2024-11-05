package com.zenveus.the_culinary_academy.bo;

import com.zenveus.the_culinary_academy.bo.custom.IMPL.UserBOIMPL;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case USER:
                return new UserBOIMPL();

            default:
                return null;
        }
    }
}
