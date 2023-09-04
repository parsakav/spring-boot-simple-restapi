package com.pgu.springboot.dto;

import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RentedDto {
    private User user;


    private Ads ads;

    private Date date;
}
