package com.epam.spring.homework3.utils;

import com.epam.spring.homework3.utils.anotations.RequiredPasswordLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordLengthValidator implements ConstraintValidator<RequiredPasswordLength, String> {

    private int passwordLength;

    @Override
    public void initialize(RequiredPasswordLength constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        passwordLength = constraintAnnotation.passLength();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() >= passwordLength;
    }
}
