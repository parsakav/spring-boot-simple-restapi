package com.pgu.springboot.controller;

import com.pgu.springboot.dto.AdsDto;
import com.pgu.springboot.dto.CommentsDto;
import com.pgu.springboot.dto.RentedDto;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.exceptions.InvalidRoleException;
import com.pgu.springboot.exceptions.UserServiceException;
import com.pgu.springboot.model.request.RoleType;
import com.pgu.springboot.model.request.UserDetailsRequestModel;
import com.pgu.springboot.model.response.*;
import com.pgu.springboot.security.SecurityConstant;
import com.pgu.springboot.service.CommentsService;
import com.pgu.springboot.service.RentedService;
import com.pgu.springboot.service.RoleService;
import com.pgu.springboot.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.pgu.springboot.service.UserService.setRole;

@RestController
@RequestMapping("users")

public class UserController {

    @Autowired
    private RentedService rentedService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    @Lazy
    private UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE
            ,MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody @Valid UserDetailsRequestModel userDetails, BindingResult bindingResult) throws Exception {

       String role = getRoleNameFromRoleEnum(userDetails.getRole());

        if(bindingResult.hasErrors()) {

            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        } else if(role==null){
            throw  new InvalidRoleException("RoleValidatorConstraint:"+userDetails.getRole()+" is invalid");
        }

        UserRest returnValue = new UserRest();
        UserDto userDto= new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setRole(role);
        UserDto createdUser= userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }
private String getRoleNameFromRoleEnum(String role){
        for(RoleType roleType: RoleType.values()){
            if(roleType.name().equalsIgnoreCase(role) || roleType.getName().equalsIgnoreCase(role)){
                return roleType.getName();
            }
        }
        return null;
}

    @GetMapping(path ="{id}",produces = {MediaType.APPLICATION_JSON_VALUE
            , MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable("id") String userId) {
        UserRest returnValue = new UserRest();
        UserDto userDto=userService.getUserById(userId);

        BeanUtils.copyProperties(userDto, returnValue);
     List<AdsRest> adsRests=   convertAdsDtoToAdsRest(userDto.getAds());

returnValue.setAds(adsRests);
        return returnValue;

    }
    @PreAuthorize("hasRole('ROLE_RENTER')")
    @PostMapping("{ads_id}")
    public RentedRest rent(@PathVariable Long ads_id,@RequestHeader("Authorization") String authorization){
    // TODO Auto-generated method stub
        RentedDto rentedDto= rentedService.rent(authorization,ads_id);
RentedRest rentedRest = new RentedRest();

        rentedRest.setUserId(rentedDto.getUser().getUserId());
        rentedRest.setAdsPrice(rentedDto.getAds().getPrice());
        rentedRest.setDate(rentedDto.getDate());
        rentedRest.setAdsDescription(rentedDto.getAds().getDescription());
        rentedRest.setAdsTitle(rentedDto.getAds().getTitle());
return rentedRest;
    }
    @PreAuthorize("hasRole('ROLE_RENTER')")
    @PostMapping("{ads_id}/{text}")
    public CommentRest comment(@PathVariable Long ads_id, @PathVariable String text, @RequestHeader("Authorization") String authorization){
        CommentsDto rentedDto= commentsService.comment(authorization,ads_id,text);
CommentRest rentedRest = new CommentRest();

        rentedRest.setUserId(rentedDto.getUser().getUserId());
        rentedRest.setComment(text);
   
return rentedRest;
    }

    private static List<AdsRest> convertAdsDtoToAdsRest(Collection<AdsDto> adsDtos){

        List<AdsRest> returnValue= new ArrayList<>();
        for(AdsDto ad: adsDtos ){
            AdsRest adsDto=new AdsRest();

            BeanUtils.copyProperties(ad, adsDto);

            returnValue.add(adsDto);

        }
        return returnValue;
    }

}
