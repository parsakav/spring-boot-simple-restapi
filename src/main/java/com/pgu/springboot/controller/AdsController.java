package com.pgu.springboot.controller;

import com.pgu.springboot.dto.AdsDto;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.AdsLimit;
import com.pgu.springboot.model.request.AdsDetailsRequestModel;
import com.pgu.springboot.model.request.RoleType;
import com.pgu.springboot.model.response.AdsRest;
import com.pgu.springboot.model.response.ErrorMessages;
import com.pgu.springboot.model.response.UserRest;
import com.pgu.springboot.service.AdsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("ads")
public class AdsController {

    @Autowired
    private AdsService adsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("{ads_id}")
    public ResponseEntity<Boolean> confirmAds(@PathVariable Long ads_id){

       Boolean returnValue= adsService.confirmAds(AdsDto.builder().id(ads_id).build());

        return new ResponseEntity<>(returnValue, HttpStatus.OK);

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE
            , MediaType.APPLICATION_XML_VALUE})

    @PreAuthorize("hasRole('ROLE_LESSOR')")

    public AdsRest createAds(@RequestBody @Valid AdsDetailsRequestModel adsDetails, BindingResult bindingResult) throws Exception {
     setDefaultAdsLimit(adsDetails);
        AdsLimit role = getAdsLimitNameFromAdsLimitEnum(adsDetails.getAdsLimit());

        if (bindingResult.hasErrors()) {

            throw new IllegalArgumentException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        AdsRest returnValue = new AdsRest();
        AdsDto dto = new AdsDto();
        BeanUtils.copyProperties(adsDetails,dto);
        dto.setAdsLimit(role);

        dto= adsService.createAds(dto);
       BeanUtils.copyProperties(dto,returnValue);
        return returnValue;
    }
    private void setDefaultAdsLimit(AdsDetailsRequestModel adsDetailsRequestModel){
       if( adsDetailsRequestModel.getAdsLimit()==null || adsDetailsRequestModel.getAdsLimit().isEmpty()){
           adsDetailsRequestModel.setAdsLimit("One month");
       }
    }
    private AdsLimit getAdsLimitNameFromAdsLimitEnum(String role){
        for(AdsLimit roleType: AdsLimit.values()){
            if(roleType.name().equalsIgnoreCase(role) || roleType.getName().equalsIgnoreCase(role)){
                return roleType;
            }
        }
        return null;
    }

@PreAuthorize("hasAnyRole('ROLE_RENTER','ROLE_ADMIN')")
    @GetMapping
    public List<AdsRest> getAds() {
    List<AdsDto> adsDtos = adsService.getAds();
    List<AdsRest> returnValue= new ArrayList<>();
    boolean isAdmin=false;
    for(GrantedAuthority grantedAuthority:SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
        if(grantedAuthority.getAuthority().toLowerCase().contains("admin")){
            System.out.println(grantedAuthority.getAuthority());
isAdmin=true;
        break;}
    }
    convertAdsDtoToAdsRest(adsDtos,returnValue,isAdmin);


    return returnValue;

    }

    public static void convertAdsDtoToAdsRest(List<AdsDto> adsDtos,List<AdsRest> returnValue,boolean isAdmin){
        for(AdsDto ad: adsDtos){
            if(isAdmin) {
                AdsRest adsRest = new AdsRest();

                BeanUtils.copyProperties(ad, adsRest);
                returnValue.add(adsRest);
            } else {
                if (ad.isConfirm()) {
                    AdsRest adsRest = new AdsRest();

                    BeanUtils.copyProperties(ad, adsRest);
                    returnValue.add(adsRest);
                }
            }
        }
    }


}
