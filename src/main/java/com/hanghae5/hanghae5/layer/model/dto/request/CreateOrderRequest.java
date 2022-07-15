package com.hanghae5.hanghae5.layer.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotNull
    private Long restaurantId;
    @NotNull
    private List<FoodRequest> foods;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FoodRequest{
        private Long id;
        @Range(min = 1,max = 100)
        private int quantity;
    }
}
