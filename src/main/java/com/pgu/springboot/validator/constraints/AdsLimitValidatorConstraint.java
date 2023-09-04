package com.pgu.springboot.validator.constraints;

import com.pgu.springboot.validator.AdsLimitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdsLimitValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdsLimitValidatorConstraint {
    String message() default "Invalid ads limit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}