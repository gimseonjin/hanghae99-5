package com.hanghae5.hanghae5.IntegrationTest.integrationSetting;

import com.hanghae5.hanghae5.layer.infra.FoodRepository;
import com.hanghae5.hanghae5.layer.infra.OrderQuantityRepository;
import com.hanghae5.hanghae5.layer.infra.OrderRepository;
import com.hanghae5.hanghae5.layer.infra.RestaurantRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

public class IntegrationTestDBClean {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderQuantityRepository orderQuantityRepository;

    @Autowired
    private FoodRepository foodRepository;

    @BeforeAll
    public void deleteAllBeforeTest(){
        orderQuantityRepository.deleteAll();
        foodRepository.deleteAll();
        orderRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @AfterAll
    public void deleteAllAfterTest(){
        orderQuantityRepository.deleteAll();
        foodRepository.deleteAll();
        orderRepository.deleteAll();
        restaurantRepository.deleteAll();
    }
}
