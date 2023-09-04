package com.pgu.springboot.model.response;

import com.pgu.springboot.dto.AdsDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.Role;
import com.pgu.springboot.model.request.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String occupation;
    private String role;
    private List<AdsRest> ads;


}
