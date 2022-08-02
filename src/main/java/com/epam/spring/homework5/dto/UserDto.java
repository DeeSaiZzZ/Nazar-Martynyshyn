package com.epam.spring.homework5.dto;

import com.epam.spring.homework5.dto.groups.OnCreate;
import com.epam.spring.homework5.dto.groups.OnUpdate;
import com.epam.spring.homework5.utils.anotations.RequiredPasswordLength;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private int id;

    @NotBlank(message = "{user.firstName.NotBlank}}")
    private String name;

    @NotBlank(message = "{user.lastName.NotBlank}}")
    private String surname;

    @Email(message = "{user.email.Email}")
    @NotBlank(message = "{user.email.NotBlank}")
    private String email;

    @NotBlank(message = "{user.password.NotBlank}", groups = OnCreate.class)
    @Null(message = "{user.password.Null}", groups = OnUpdate.class)
    @RequiredPasswordLength(passLength = 10, message = "{user.password.RequiredPasswordLength}", groups = OnCreate.class)
    @Schema(description = "Password must be longer then 10 symbols")
    @ToString.Exclude
    private String password;
}
