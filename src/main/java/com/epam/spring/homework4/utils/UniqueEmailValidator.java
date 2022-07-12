package com.epam.spring.homework4.utils;

import com.epam.spring.homework4.repository.MasterRepository;
import com.epam.spring.homework4.repository.UserRepository;
import com.epam.spring.homework4.utils.anotations.UniqueEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserRepository userRepository;
    private MasterRepository masterRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(userRepository.getAllUser(), masterRepository.getAllMaster())
                .flatMap(Collection::stream)
                .noneMatch(user -> user.getEmail().equals(email));
    }
}
