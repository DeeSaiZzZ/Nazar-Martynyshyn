package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.dto.groups.OnCreate;
import com.epam.spring.homework3.dto.groups.OnUpdate;
import com.epam.spring.homework3.utils.anotations.RequiredPasswordLength;
import com.epam.spring.homework3.utils.anotations.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @UniqueEmail
    @Email(message = "Doesn't math email pattern")
    @NotBlank(message = "Email shouldn't be empty")
    private String email;

    @NotBlank(message = "Password shouldn't be empty", groups = OnCreate.class)
    @Null(message = "Password should by empty when entity update", groups = OnUpdate.class)
    @RequiredPasswordLength(passLength = 10)
    @Schema(description = "Password must be longer then 10 symbols")
    private String password;
}
