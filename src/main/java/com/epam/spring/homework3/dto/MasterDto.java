package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.dto.groups.OnCreate;
import com.epam.spring.homework3.dto.groups.OnUpdate;
import com.epam.spring.homework3.model.enums.Speciality;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterDto {

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

    @DecimalMax(value = "0.0", groups = OnCreate.class)
    @Negative(message = "Cannot by negative")
    private double rate;

    private Speciality speciality;
}
