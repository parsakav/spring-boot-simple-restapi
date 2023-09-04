package com.pgu.springboot.controller;

import com.pgu.springboot.exceptions.InvalidRoleException;
import com.pgu.springboot.exceptions.UserServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({UserServiceException.class,IllegalArgumentException.class, InvalidRoleException.class})
    public ResponseEntity<String> handleExceptions(Throwable throwable, WebRequest webRequest){

        return new ResponseEntity<>(throwable.getMessage(),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}