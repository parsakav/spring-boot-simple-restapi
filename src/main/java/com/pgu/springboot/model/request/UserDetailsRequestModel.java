package com.pgu.springboot.model.request;

import com.pgu.springboot.validator.constraints.RoleValidatorConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class UserDetailsRequestModel {

    @NotNull(message = "Firstname may not be null")
    @NotEmpty(message = "Firstname may not be empty")
    private String firstName;
    @NotNull(message = "Lastname may not be null")
    @NotEmpty(message = "Lastname may not be empty")
    private String lastName;
    @NotNull(message = "Username may not be null")
    @NotEmpty(message = "Username may not be empty")

    private String username;
    @NotNull(message = "Password may not be null")
    @NotEmpty(message = "Password may not be empty")
    private String password;
    @NotNull(message = "Occupation may not be null")
    @NotEmpty(message = "Occupation may not be empty")
    private String occupation;
    @NotNull(message = "RoleType may not be null")
    @RoleValidatorConstraint
    private String role;

}
