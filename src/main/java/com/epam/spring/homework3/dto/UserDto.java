package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.dto.groups.OnCreate;
import com.epam.spring.homework3.dto.groups.OnUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private int id;

    @NotBlank(message = "First shouldn't be empty")
    private String firstName;

    @NotBlank(message = "Lastname shouldn't be empty")
    private String lastName;

    @Email(message = "Doesn't math email pattern")
    @NotBlank(message = "Email shouldn't be empty")
    private String email;

    @NotBlank(message = "Password shouldn't be empty", groups = OnCreate.class)
    @Null(message = "Password should by empty when entity update", groups = OnUpdate.class)
    private String password;
}
