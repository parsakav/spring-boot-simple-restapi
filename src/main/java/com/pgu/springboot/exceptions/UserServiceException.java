package com.pgu.springboot.exceptions;

public class UserServiceException extends Exception {
    public UserServiceException(String  errorMessage) {
        super(errorMessage);
    }
}
