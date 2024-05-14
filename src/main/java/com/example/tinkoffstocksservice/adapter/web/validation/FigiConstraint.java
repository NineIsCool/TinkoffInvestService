package com.example.tinkoffstocksservice.adapter.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FigiValidator.class)
public @interface FigiConstraint {
    String message() default "Figi должен состоять из 12 символов ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
