package com.zenveus.the_culinary_academy.bo.custom.IMPL;

import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.UserDTO;
import com.zenveus.the_culinary_academy.entity.User;


public class UserBOIMPL implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);



    @Override
    public boolean addUser(UserDTO userDTO) {

        try {
            userDAO.add(new User(userDTO.getUserId(), userDTO.getFullName(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getAddress(), userDTO.getUsername(), userDTO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return false;
    }
}
