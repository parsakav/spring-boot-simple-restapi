package com.pgu.springboot.validator.constraints;

import com.pgu.springboot.validator.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
    @Constraint(validatedBy = RoleValidator.class)
    @Target( { ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RoleValidatorConstraint {
        String message() default "Invalid role";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

