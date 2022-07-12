package com.epam.spring.homework3.utils.anotations;

import com.epam.spring.homework3.utils.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email exist please enter other email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
