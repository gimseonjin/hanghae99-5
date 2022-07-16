package com.hanghae5.hanghae5.config.validation.annotation;

import com.hanghae5.hanghae5.config.validation.CheckMoneyUnitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckMoneyUnitValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMoneyUnit {
    String message() default "최소 금액 단위가 맞지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int unit() default 100;
}