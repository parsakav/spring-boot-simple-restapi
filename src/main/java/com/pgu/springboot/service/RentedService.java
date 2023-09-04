package com.pgu.springboot.service;

import com.pgu.springboot.dto.RentedDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.Rented;
import com.pgu.springboot.entity.User;
import com.pgu.springboot.repository.RentedRepository;
import com.pgu.springboot.repository.UserRepository;
import com.pgu.springboot.security.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class RentedService {
    private final UserService userService;
    private final AdsService adsService;

    private final RentedRepository rentedRepository;

    public RentedService(RentedRepository rentedRepository, AdsService adsService, UserService userService) {
        this.rentedRepository = rentedRepository;
        this.adsService = adsService;
        this.userService = userService;
    }

    public RentedDto save(Rented rented){
       Rented r= rentedRepository.save(Objects.requireNonNull(rented));
        RentedDto rentedDto = new RentedDto();
        BeanUtils.copyProperties(r,rentedDto);
        return rentedDto;
    }
    public RentedDto rent(String authorization, Long ads_id) {
        String username = Jwts.parserBuilder().setSigningKey(SecurityConstant.getSigningKey()).build()
                .parseClaimsJws(authorization.replace("Bearer", "").trim()).getBody().getSubject();

        Optional<User> user=userService.findUserByName(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);

        }
        Ads ads= adsService.getAds(ads_id);
        Rented rented = new Rented();
        rented.setUser(user.get());
        rented.setAds(ads);
        rented.setDate(new Date());
ads.setRented(rented);
       return this.save(rented);

    }
}
