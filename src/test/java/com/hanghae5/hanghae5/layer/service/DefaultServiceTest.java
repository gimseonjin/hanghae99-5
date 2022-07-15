package com.hanghae5.hanghae5.layer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae5.hanghae5.layer.infra.FoodRepository;
import com.hanghae5.hanghae5.layer.infra.OrderQuantityRepository;
import com.hanghae5.hanghae5.layer.infra.OrderRepository;
import com.hanghae5.hanghae5.layer.infra.RestaurantRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultServiceTest {

    @Mock
    protected OrderRepository orderRepository;
    @Mock
    protected RestaurantRepository restaurantRepository;
    @Mock
    protected FoodRepository foodRepository;

    @Mock
    protected OrderQuantityRepository orderQuantityRepository;

    protected ObjectMapper objectMapper = new ObjectMapper();
}
