package com.hyperit.flightadvisor.mapstruct;

import com.hyperit.flightadvisor.bean.UserDto;
import com.hyperit.flightadvisor.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

}
