package com.hanghae5.hanghae5.IntegrationTest;

import com.hanghae5.hanghae5.layer.infra.FoodRepository;
import com.hanghae5.hanghae5.layer.infra.OrderQuantityRepository;
import com.hanghae5.hanghae5.layer.infra.OrderRepository;
import com.hanghae5.hanghae5.layer.infra.RestaurantRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DefaultIntegrationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderQuantityRepository orderQuantityRepository;

    @Autowired
    private FoodRepository foodRepository;


    // =================================================
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
    // =================================================
}
