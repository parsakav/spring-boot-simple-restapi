package com.pgu.springboot.validator;

import com.pgu.springboot.entity.AdsLimit;
import com.pgu.springboot.model.request.RoleType;
import com.pgu.springboot.validator.constraints.AdsLimitValidatorConstraint;
import com.pgu.springboot.validator.constraints.RoleValidatorConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AdsLimitValidator  implements ConstraintValidator<AdsLimitValidatorConstraint,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
          for(AdsLimit roleType: AdsLimit.values()){
            if(roleType.name().equalsIgnoreCase(value) || roleType.getName().equalsIgnoreCase(value)){
                return true;
            }
        }

        return false;
    }
}
