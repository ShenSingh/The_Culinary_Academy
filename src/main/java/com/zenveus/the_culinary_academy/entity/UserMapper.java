package com.zenveus.the_culinary_academy.entity;

import com.zenveus.the_culinary_academy.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
