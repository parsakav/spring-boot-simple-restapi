package com.pgu.springboot.model.response;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field.Please check documentation for required fields.")
    ,RECORD_ALREADY_EXISTS("Record is already exists")
    ,USERID_NOT_FOUND("There is no user with this user id"),
    ROLE_NOT_FOUND("There is no role with this user")
    ;

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
