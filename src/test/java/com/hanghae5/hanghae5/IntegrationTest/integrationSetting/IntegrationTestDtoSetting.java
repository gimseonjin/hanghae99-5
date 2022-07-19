package com.hanghae5.hanghae5.IntegrationTest.integrationSetting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public interface IntegrationTestDtoSetting {

    @Getter
    @Setter
    @Builder
    static class RestaurantDto {
        public Long id;
        public String name;
        public int minOrderPrice;
        public int deliveryFee;
    }

    @Getter
    @Setter
    @Builder
    static class OrderRequestDto {
        public Long restaurantId;
        public List<FoodOrderRequestDto> foods;
    }

    @Getter
    @Setter
    @Builder
    static class FoodOrderRequestDto {
        public Long id;
        public int quantity;
    }

    @Getter
    @Setter
    static class OrderDto {
        public String restaurantName;
        public List<FoodOrderDto> foods;
        public int deliveryFee;
        public int totalPrice;
    }

    @Getter
    @Setter
    static class FoodOrderDto {
        public String name;
        public int quantity;
        public int price;
    }

    @Getter
    @Setter
    @Builder
    static class FoodDto {
        public Long id;
        public String name;
        public int price;
    }
}
