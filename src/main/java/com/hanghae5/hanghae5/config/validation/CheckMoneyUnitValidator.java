package com.hanghae5.hanghae5.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckMoneyUnitValidator implements ConstraintValidator<CheckMoneyUnit, Integer> {

    private int unit;

    @Override
    public void initialize(CheckMoneyUnit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.unit = constraintAnnotation.unit();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % unit == 0;
    }

}
