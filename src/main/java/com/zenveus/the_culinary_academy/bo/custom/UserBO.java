package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean addUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();

    boolean updateUser(UserDTO userDto);

    boolean deleteUser(UserDTO userDto);

    UserDTO isUserExist(UserDTO userDTO);
}
