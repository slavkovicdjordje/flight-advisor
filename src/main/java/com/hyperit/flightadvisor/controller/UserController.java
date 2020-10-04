package com.hyperit.flightadvisor.controller;

import com.hyperit.flightadvisor.bean.UserDto;
import com.hyperit.flightadvisor.entity.User;
import com.hyperit.flightadvisor.mapstruct.UserMapper;
import com.hyperit.flightadvisor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);

        return UserMapper.INSTANCE.userToUserDto(userService.saveUser(user));
    }
}
