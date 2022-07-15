package com.hanghae5.hanghae5.layer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.AddMenuRequest;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateRestaurantRequest;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTest extends DefaultServiceTest{
    @BeforeEach
    public void setup(){

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

        Restaurant fakeRestaurant = Restaurant.builder().name("쉐이크쉑 청담점").deliveryFee(2000).orders(
                new ArrayList<>()
        ).minOrderPrice(15000).menu(
                new ArrayList<>(Arrays.asList(fakeFood1,fakeFood2,fakeFood3))
        ).build();
        ReflectionTestUtils.setField(fakeRestaurant, "id", 1L);

        given(restaurantRepository.findById(1l))
                .willReturn(Optional.of(fakeRestaurant));

        given(foodRepository.findById(1l))
                .willReturn(Optional.of(fakeFood1));

        given(foodRepository.findById(2l))
                .willReturn(Optional.of(fakeFood2));

        given(foodRepository.findById(3l))
                .willReturn(Optional.of(fakeFood3));

    }

    @DisplayName("1. 음식점 생성 성공하기")
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void test1() throws JsonProcessingException {

        // Given
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);

        CreateRestaurantRequest createRestaurantRequest = CreateRestaurantRequest.builder()
                .name("쉐이크쉑 청담점")
                .minOrderPrice(15000)
                .deliveryFee(2000)
                .build();

        // When
        restaurantService.addRestaurant(createRestaurantRequest);

        // Then
        // 아무일도 일어나지 않으면 성공
    }

    @DisplayName("2. 음식점 조회 성공하기")
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void test2() throws JsonProcessingException {
        // Given
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        Restaurant fakeRestaurant = Restaurant.builder().name("쉐이크쉑 청담점").deliveryFee(2000).orders(
                new ArrayList<>()
        ).minOrderPrice(15000).build();
        ReflectionTestUtils.setField(fakeRestaurant, "id", 1L);

        given(restaurantRepository.findAll())
                .willReturn(List.of(fakeRestaurant));

        // When
        List<Restaurant> results = restaurantService.getAllRestaurant();

        // Then
        assertTrue(results.contains(fakeRestaurant));
    }

    @DisplayName("3. 음식점 메뉴 등록 성공하기")
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void test3() throws JsonProcessingException {
        // Given
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        AddMenuRequest addMenuRequest = AddMenuRequest.builder()
                .name("쉑버거 더블")
                .price(8900)
                .build();

        // When
        restaurantService.addMenus(1L, List.of(addMenuRequest));

        // Then
        // 아무일도 일어나지 않으면 성공
    }

    @DisplayName("4. 음식점 메뉴 가져오기 성공하기")
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void test4() throws JsonProcessingException {
        // Given
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);

        // When
        List<Food> results =  restaurantService.getMenus(1L);

        // Then
        boolean isNotAllFoodExist = results.stream()
                .filter(food -> food.getName().equals("쉑버거 더블") && food.getPrice() == 10900)
                .filter(food -> food.getName().equals("치즈 감자튀김") && food.getPrice() == 9800)
                .filter(food -> food.getName().equals("쉐이크") && food.getPrice() == 17700)
                .findAny().isPresent();
        assertFalse(isNotAllFoodExist);
    }


    // 음식점 내 음식 중복은 별도의 테스트 하지 않음 -> 왜냐하면 테이블의 멀티 유니크 속성으로 예외처리하기 때문
    // 허용값 단위는 별도의 테스트 하지 않음 -> 왜냐하면 이건 Validation 기능이기 때문
}