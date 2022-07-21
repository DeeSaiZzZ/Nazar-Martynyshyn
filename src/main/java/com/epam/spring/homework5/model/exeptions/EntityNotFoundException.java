package com.epam.spring.homework5.model.exeptions;

import com.epam.spring.homework5.model.enums.ErrorType;

public class EntityNotFoundException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Entity not found";

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.VALIDATION_ERROR_TYPE;
    }
}
