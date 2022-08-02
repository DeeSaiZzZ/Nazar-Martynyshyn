package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.model.Error;
import com.epam.spring.homework5.model.enums.ErrorType;
import com.epam.spring.homework5.model.exeptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        log.error("handleMethodArgumentNotValidException: exception {}", exp.getMessage(), exp);
        return exp.getBindingResult().getAllErrors().stream()
                .map(err -> new Error(
                        err.getDefaultMessage(), ErrorType.VALIDATION_ERROR_TYPE, LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleServiceException(ServiceException exp, HandlerMethod handlerMethod) {
        log.error("handleServiceException: message: {}, method: {}", exp.getMessage(),
                handlerMethod.getMethod(), exp);
        return new Error(exp.getMessage(), exp.getErrorType(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handlerException(ServiceException exp, HandlerMethod handlerMethod) {
        log.error("handlerException: message: {}, method: {}", exp.getMessage(),
                handlerMethod.getMethod(), exp);
        return new Error(exp.getMessage(), ErrorType.FATAL_ERROR_TYPE, LocalDateTime.now());
    }
}
