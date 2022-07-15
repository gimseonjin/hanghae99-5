package com.hanghae5.hanghae5.layer.model.dto.request;


import com.hanghae5.hanghae5.config.validation.CheckMoneyUnit;
import com.hanghae5.hanghae5.layer.model.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMenuRequest {

    @NotNull
    private String name;

    @NotNull
    @Range(min = 100, max = 1000000)
    @CheckMoneyUnit(unit = 100)
    private int price;

    public Food toFood(){
        return Food.builder()
                .name(name)
                .price(price)
                .build();
    }
}
