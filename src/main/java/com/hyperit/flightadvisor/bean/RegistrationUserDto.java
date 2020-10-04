package com.hyperit.flightadvisor.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationUserDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
