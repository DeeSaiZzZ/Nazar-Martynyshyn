package com.epam.spring.homework5.utils.anotations;

import com.epam.spring.homework5.utils.PasswordLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordLengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredPasswordLength {

    int passLength() default 0;

    String message() default "Password must be longer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
