package com.example.IdentityService.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDateConstraint {
    String message() default "Invalid Birth Date";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
