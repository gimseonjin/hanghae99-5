package com.hanghae5.hanghae5.layer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Ordel;
import com.hanghae5.hanghae5.layer.model.OrderQuantity;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateOrderRequest;
import com.hanghae5.hanghae5.layer.model.dto.response.GetOrdersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class OrderServiceTest extends DefaultServiceTest{

    @BeforeEach
    public void setup(){
        Restaurant fakeRestaurant = Restaurant.builder().name("쉐이크쉑 청담점").deliveryFee(2000).orders(
                new ArrayList<>()
        ).minOrderPrice(15000).build();
        ReflectionTestUtils.setField(fakeRestaurant, "id", 1L);

        Food fakeFood1 = Food.builder().name("쉑버거 더블").price(10900).orderQuantityList(
                new ArrayList<>()
        ).build();
        Food fakeFood2 = Food.builder().name("치즈 감자튀김").price(9800).orderQuantityList(
                new ArrayList<>()
        ).build();
        Food fakeFood3 = Food.builder().name("쉐이크").price(17700).orderQuantityList(
                new ArrayList<>()
        ).build();

        ReflectionTestUtils.setField(fakeFood1, "id", 1L);
        ReflectionTestUtils.setField(fakeFood2, "id", 2L);
        ReflectionTestUtils.setField(fakeFood3, "id", 3L);

        OrderQuantity fakeOrderQuantity1 = OrderQuantity.builder().food(fakeFood1).quantity(1).build();
        OrderQuantity fakeOrderQuantity2 = OrderQuantity.builder().food(fakeFood2).quantity(2).build();
        OrderQuantity fakeOrderQuantity3 = OrderQuantity.builder().food(fakeFood3).quantity(3).build();

        ReflectionTestUtils.setField(fakeOrderQuantity1, "id", 1L);
        ReflectionTestUtils.setField(fakeOrderQuantity2, "id", 2L);
        ReflectionTestUtils.setField(fakeOrderQuantity3, "id", 3L);

        Ordel fakeOrder = Ordel.builder().restaurant(fakeRestaurant).orderQuantityList(
                new ArrayList<>(Arrays.asList(fakeOrderQuantity1, fakeOrderQuantity2, fakeOrderQuantity3))
        ).build();

        ReflectionTestUtils.setField(fakeOrder, "id", 1L);

        given(restaurantRepository.findById(1l))
                .willReturn(Optional.of(fakeRestaurant));

        given(foodRepository.findById(1l))
                .willReturn(Optional.of(fakeFood1));

        given(foodRepository.findById(2l))
                .willReturn(Optional.of(fakeFood2));

        given(foodRepository.findById(3l))
                .willReturn(Optional.of(fakeFood3));

        given(orderRepository.save(any())).willReturn(fakeOrder);

    }

    @DisplayName("1. 주문 생성하기")
    @Test
    public void test1() throws JsonProcessingException {

        // Given
        OrderService orderService = new OrderService(orderRepository, foodRepository, restaurantRepository);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .restaurantId(1L)
                .foods(
                        List.of(
                                new CreateOrderRequest.FoodRequest(1L,1),
                                new CreateOrderRequest.FoodRequest(2L,2),
                                new CreateOrderRequest.FoodRequest(3L,3)
                                )
                )
                .build();

        // When
        GetOrdersResponse order = orderService.order(createOrderRequest);

        // Then
        // response 이름이 제대로 들어갔는가?
        assertEquals("쉐이크쉑 청담점", order.getRestaurantName());

        // 음식이 제대로 매핑됐는가?
        boolean isNotFoodsCollect = order.getFoods().stream()
                .filter(foods -> !(foods.getName().equals("쉑버거 더블") && foods.getPrice() == 10900))
                .filter(foods -> !(foods.getName().equals("치즈 감자튀김") && foods.getPrice() == 19600))
                .filter(foods -> !(foods.getName().equals("쉐이크") && foods.getPrice() == 53100))
                .findAny().isPresent();
        assertEquals(false, isNotFoodsCollect);

        // 배달비가 제대로 포함되었는가?
        assertEquals(2000, order.getDeliveryFee());

        // 최종 금액이 제대로 계산되었는가?
        assertEquals(85600, order.getTotalPrice());
    }

    @DisplayName("2. 주문 실패하기 - 최소 주문 금액 미달")
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void test2() throws JsonProcessingException {

        // Given
        OrderService orderService = new OrderService(orderRepository, foodRepository, restaurantRepository);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .restaurantId(1L)
                .foods(
                        List.of(
                                // 최소 주문을 미달시키기 위해 9800원자리만 추가
                                new CreateOrderRequest.FoodRequest(2L,1)
                        )
                )
                .build();

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.order(createOrderRequest));

        // Then
        assertEquals("배달비가 최소 금액을 넘어야합니다.", exception.getMessage());
    }

    // 최소 & 최대 주문 갯수 테스트 생략 -> 왜냐하면 그건 Validation 기능이기 때문
    // 모든 주문 조회 테스트 생략 -> 왜냐하면 그건 JPA 기능이기 때문
}