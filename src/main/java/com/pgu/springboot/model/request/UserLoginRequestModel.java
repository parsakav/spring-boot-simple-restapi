package com.pgu.springboot.model.request;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString


    public class UserLoginRequestModel {
        @NotNull(message = "Email may not be null")
        @NotEmpty(message = "Email may not be empty")


        private String username;
        @NotNull(message = "Password may not be null")
        @NotEmpty(message = "Password may not be empty")
        private String password;

    }
