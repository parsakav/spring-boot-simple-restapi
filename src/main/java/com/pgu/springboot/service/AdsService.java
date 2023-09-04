package com.pgu.springboot.service;

import com.pgu.springboot.dto.AdsDto;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.Comments;
import com.pgu.springboot.entity.Role;
import com.pgu.springboot.entity.User;
import com.pgu.springboot.model.response.AdsRest;
import com.pgu.springboot.repository.AdsRepository;
import com.pgu.springboot.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional

public class AdsService {
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public AdsService(AdsRepository adsRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public AdsDto createAds(AdsDto dto) {
        AdsDto returnValue = new AdsDto();
        User u = userRepository.findByUserId(dto.getUserId());
        Ads ads = new Ads();
        BeanUtils.copyProperties(dto, ads);
        ads.setUser(u);

        ads.setDate(new Date());
        ads = adsRepository.save(ads);
        BeanUtils.copyProperties(ads, returnValue);

        returnValue.setUserId(u.getUserId());
        return returnValue;

    }
 static List<AdsDto> convertAdsEntityToAdsDto(Collection<Ads> adsList){

     List<AdsDto> returnValue= new ArrayList<>();
     for(Ads ad: adsList){

             AdsDto adsDto = new AdsDto();

             BeanUtils.copyProperties(ad, adsDto);
             adsDto.setUserId(ad.getUser().getUserId());
             List<String> message = new ArrayList<>();
             for(Comments com:ad.getCommentsSet()) {
                 message.add(com.getText());
             }
adsDto.setMessages(message);
             returnValue.add(adsDto);


     }
     return returnValue;
 }

    public List<AdsDto> getAds() {
List<Ads> adsList= adsRepository.findAll();
List<AdsDto> returnValue= convertAdsEntityToAdsDto(adsList);
    return returnValue;
}

 Ads getAds(Long ads_id) {
Ads adsList= adsRepository.getReferenceById(ads_id);

    return adsList;
}

    public Boolean confirmAds(AdsDto adsDto) {
      Ads ads=  adsRepository.getReferenceById(adsDto.getId());
        if(ads==null){
            return false;
        }
        ads.setConfirm(true);

             ads=   adsRepository.save(ads);

        return true;
    }
}
