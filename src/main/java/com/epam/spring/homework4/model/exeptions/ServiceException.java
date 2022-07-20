package com.epam.spring.homework4.model.exeptions;

import com.epam.spring.homework4.model.enums.ErrorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ServiceException extends RuntimeException {

    protected ServiceException(String message) {
        super(message);
    }


    public ErrorType getErrorType() {
        return ErrorType.FATAL_ERROR_TYPE;
    }
}
