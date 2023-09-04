package com.pgu.springboot.model.request;

import com.pgu.springboot.entity.User;
import com.pgu.springboot.validator.constraints.AdsLimitValidatorConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class AdsDetailsRequestModel {
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @Range(min = 0)
    private double price;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String userId;
    @NotNull
    @NotEmpty
    @AdsLimitValidatorConstraint
    private String adsLimit;

}
