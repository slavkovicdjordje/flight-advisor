package com.hyperit.flightadvisor.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
public class UserDto {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String firstName;
    @NotBlank(message = "Name is mandatory")
    private String lastName;
    @NotBlank(message = "Name is mandatory")
    private String username;
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank(message = "Name is mandatory")
    private String password;

}
