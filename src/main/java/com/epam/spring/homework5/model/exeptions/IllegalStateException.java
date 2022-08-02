package com.epam.spring.homework5.model.exeptions;

import com.epam.spring.homework5.model.enums.ErrorType;

public class IllegalStateException extends ServiceException {
    public IllegalStateException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.VALIDATION_ERROR_TYPE;
    }
}
