package com.pgu.springboot.exceptions;

public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String msg){
        super(msg);
    }
}
