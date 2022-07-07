package com.epam.spring.homework3.model;

import com.epam.spring.homework3.model.enums.Role;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "user")
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
