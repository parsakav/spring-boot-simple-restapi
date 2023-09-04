package com.pgu.springboot.validator;

import com.pgu.springboot.model.request.RoleType;
import com.pgu.springboot.validator.constraints.RoleValidatorConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<RoleValidatorConstraint,String> {
    @Override
    public void initialize(RoleValidatorConstraint constraintAnnotation) {
        // ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for(RoleType roleType: RoleType.values()){
            if(roleType.name().equalsIgnoreCase(value) || roleType.getName().equalsIgnoreCase(value)){
                return true;
            }
        }

    return false;
    }
}
