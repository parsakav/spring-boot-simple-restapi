package com.pgu.springboot.dto;

import com.pgu.springboot.entity.AdsLimit;
import com.pgu.springboot.entity.Comments;
import com.pgu.springboot.entity.Rented;
import com.pgu.springboot.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdsDto {

    private Long id;

    private boolean confirm;

    private String title;

    private double price;

    private String description;

    private String userId;

    private Rented rented;


    private List<String> messages;

    private AdsLimit adsLimit;
    private Date date;


}
