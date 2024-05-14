package com.example.tinkoffstocksservice.adapter.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FigiValidator implements ConstraintValidator<FigiConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.matches("^[a-zA-Z\\d]{12}$")) {
            return true;
        }
        return false;
    }
}
