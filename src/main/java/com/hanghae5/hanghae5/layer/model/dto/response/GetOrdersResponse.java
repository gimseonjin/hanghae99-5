package com.hanghae5.hanghae5.layer.model.dto.response;

import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Ordel;
import com.hanghae5.hanghae5.layer.model.OrderQuantity;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrdersResponse {
    private String restaurantName;
    private List<Foods> foods;
    private int deliveryFee;
    private int totalPrice;

    public GetOrdersResponse(Ordel order){
        Restaurant restaurant = order.getRestaurant();
        List<OrderQuantity> orderQuantityList = order.getOrderQuantityList();

        this.restaurantName = restaurant.getName();
        this.deliveryFee = restaurant.getDeliveryFee();
        this.foods = orderQuantityList.stream()
                .map(orderQuantity -> new Foods(orderQuantity.getFood()))
                .collect(Collectors.toList());
        this.totalPrice = this.calTotalPrice(orderQuantityList);
    }

    private int calTotalPrice(List<OrderQuantity> foods){
        return foods.stream()
                .map(food -> food.getPrice())
                .reduce(0,(f1,f2)->f1+f2) + this.deliveryFee;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Foods{
        @NotNull
        private String name;

        @NotNull
        private int price;

        public Foods(Food food){
            this.name = food.getName();
            this.price = food.getPrice();
        }
    }
}
