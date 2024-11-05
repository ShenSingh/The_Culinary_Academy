package com.zenveus.the_culinary_academy.bo.custom.IMPL;

import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.UserDTO;
import com.zenveus.the_culinary_academy.entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserBOIMPL implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);



    @Override
    public boolean addUser(UserDTO userDTO) {

        try {
            userDAO.add(new User(userDTO.getUserId(), userDTO.getFullName(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getAddress(), userDTO.getUsername(), userDTO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {

        ArrayList<UserDTO> userDtoList = new ArrayList<>();
        List<User> allUsers = null;
        try {
            allUsers = userDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (User user : allUsers) {
            userDtoList.add(new UserDTO(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getUsername(), user.getPassword()));
        }

        return userDtoList;
    }

    @Override
    public boolean updateUser(UserDTO userDto) {
        try {
            userDAO.update(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteUser(UserDTO userDto) {
        try {
            userDAO.delete(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public UserDTO isUserExist(UserDTO userDTO) {
        try {
            User user = (User) userDAO.exist(userDTO.getUserId());
            if (user != null) {
                return new UserDTO(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getUsername(), user.getPassword());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userDTO;
    }
}
