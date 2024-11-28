package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserBOIMPL implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean addUser(UserDto userDto) throws Exception {
        try {
            userDAO.add(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getJobRole(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<UserDto> getAllUsers() {
        ArrayList<UserDto> userDtoList = new ArrayList<>();
        List<User> allUsers = null;
        try {
            allUsers = userDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (User user : allUsers) {
            userDtoList.add(new UserDto(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getJobRole(), user.getUsername(), user.getPassword()));
        }

        return userDtoList;
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        try {
            userDAO.update(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getJobRole(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteUser(UserDto userDto) {
        try {
            userDAO.delete(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getJobRole(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public UserDto isUserExist(UserDto userDto) {
        try {
            User user = (User) userDAO.exist(userDto.getUserId());
            if (user != null) {
                return new UserDto(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getJobRole(), user.getUsername(), user.getPassword());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }
}
