package com.pgu.springboot.dto;

import com.pgu.springboot.entity.Ads;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// data transfor object
public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String occupation;
    private String password;
    private String encryptedPassword;
    private String role;
    private List<AdsDto> ads;



}
