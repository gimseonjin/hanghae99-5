package com.hanghae5.hanghae5.layer.model.dto.request;

import com.hanghae5.hanghae5.config.validation.CheckMoneyUnit;
import com.hanghae5.hanghae5.layer.model.Restaurant;
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
public class CreateRestaurantRequest {
    @NotNull
    private String name;
    @NotNull
    @Range(min = 1000, max = 100000)
    @CheckMoneyUnit(unit = 100)
    private int minOrderPrice;
    @NotNull
    @Range(min = 0, max = 10000)
    @CheckMoneyUnit(unit = 500)
    private int deliveryFee;

    public Restaurant toRestaurant(){
        return Restaurant.builder()
                .name(name)
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();
    }
}