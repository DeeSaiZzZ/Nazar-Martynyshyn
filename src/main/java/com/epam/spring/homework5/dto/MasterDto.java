package com.epam.spring.homework5.dto;

import com.epam.spring.homework5.dto.groups.OnCreate;
import com.epam.spring.homework5.dto.groups.OnUpdate;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.utils.anotations.RequiredPasswordLength;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterDto {

    private int id;

    @NotBlank(message = "{master.firstName.NotBlank}")
    private String name;

    @NotBlank(message = "{master.lastName.NotBlank}")
    private String surname;

    @Email(message = "{master.email.Email}")
    @NotBlank(message = "{master.email.NotBlank}")
    private String email;

    @NotBlank(message = "{master.password.NotBlank}", groups = OnCreate.class)
    @Null(message = "{master.password.Null}", groups = OnUpdate.class)
    @RequiredPasswordLength(passLength = 10, message = "{master.password.RequiredPasswordLength}")
    @Schema(description = "Password must be longer then 10 symbols")
    @ToString.Exclude
    private String password;

    @DecimalMax(value = "0.0", message = "{master.rate.DecimalMax}", groups = OnCreate.class)
    @DecimalMin(value = "0.0", message = "{master.rate.DecimalMin}")
    private double rate;

    @NotNull
    private Speciality speciality;
}
