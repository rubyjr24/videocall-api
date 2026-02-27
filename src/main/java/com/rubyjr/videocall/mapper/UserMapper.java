package com.rubyjr.videocall.mapper;

import com.rubyjr.videocall.dto.UserDto;
import com.rubyjr.videocall.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserDto toDto(User user);

}
