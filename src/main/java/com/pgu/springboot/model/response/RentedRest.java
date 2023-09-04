package com.pgu.springboot.model.response;

import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.User;
import lombok.*;

import java.util.Date;

@Data
public class RentedRest {

    private String userId;
    private String adsTitle;
    private double adsPrice;
    private String adsDescription;

    private Date date;
}
