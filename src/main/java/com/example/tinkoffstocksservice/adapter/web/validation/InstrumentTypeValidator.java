package com.example.tinkoffstocksservice.adapter.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InstrumentTypeValidator implements ConstraintValidator<InstrumentTypeConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.equals("share") || value.equals("bond")
                || value.equals("currency") || value.equals("futures")
                || value.equals("option")) {
            return true;
        }
        return false;
    }
}