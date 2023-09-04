package com.pgu.springboot.model.response;

import com.pgu.springboot.entity.AdsLimit;
import com.pgu.springboot.entity.Comments;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString

public class AdsRest {

    private Long id;
    private String title;

    private double price;

    private String description;
    private List<String> messages;

    private String userId;
    private AdsLimit adsLimit;
    private Date date;
private boolean confirm;

}
