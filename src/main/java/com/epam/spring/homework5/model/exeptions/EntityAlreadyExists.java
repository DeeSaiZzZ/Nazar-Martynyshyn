package com.epam.spring.homework5.model.exeptions;

import com.epam.spring.homework5.model.enums.ErrorType;

public class EntityAlreadyExists extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Entity already exists";

    public EntityAlreadyExists() {
        super(DEFAULT_MESSAGE);
    }

    public EntityAlreadyExists(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
