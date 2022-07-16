package com.hanghae5.hanghae5.config.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import org.springframework.validation.Validator;

import java.util.Collection;

// 기본적으로 어노테이션을 활용한 Validation은 Collection 내부를 검증하지 못한다.
// 따라서 CustomValidatior를 사용하여 Collection 시, 내부 Iterate 하면서 검증한다.
@Component
public class CostumValidator implements Validator {

    private SpringValidatorAdapter validator;

    public CostumValidator() {
        this.validator = new SpringValidatorAdapter(
                Validation.buildDefaultValidatorFactory().getValidator()
        );
    }

    // 여기서 Collection인 경우만 가져와도 되는데 우선 전부 가져온다.
    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    // Collection인 경우, 내부를 탐색하면서 검증한다.
    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof Collection){
            Collection collection = (Collection) target;

            for (Object object : collection) {
                validator.validate(object, errors);
            }
        } else {
            validator.validate(target, errors);
        }

    }
}