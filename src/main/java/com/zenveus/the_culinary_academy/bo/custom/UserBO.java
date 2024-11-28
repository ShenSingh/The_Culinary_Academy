package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.UserDto;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean addUser(UserDto userDto) throws Exception;

    List<UserDto> getAllUsers();

    boolean updateUser(UserDto userDto);

    boolean deleteUser(UserDto userDto);

    UserDto isUserExist(UserDto user);
}
