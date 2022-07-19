package com.hanghae5.hanghae5.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae5.hanghae5.IntegrationTest.integrationSetting.IntegrationTestDBClean;
import com.hanghae5.hanghae5.IntegrationTest.integrationSetting.IntegrationTestDtoSetting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DefaultIntegrationTest extends IntegrationTestDBClean implements IntegrationTestDtoSetting {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected HttpHeaders headers;
    protected ObjectMapper mapper = new ObjectMapper();

    protected RestaurantDto registeredRestaurant;

    protected final List<RestaurantIntegrationTest.RestaurantDto> registeredRestaurants = new ArrayList<>();

    protected FoodIntegrationTest.FoodDto food1 = FoodIntegrationTest.FoodDto.builder()
            .id(null)
            .name("쉑버거 더블")
            .price(10900)
            .build();

    protected FoodIntegrationTest.FoodDto food2 = FoodIntegrationTest.FoodDto.builder()
            .id(null)
            .name("치즈 감자튀김")
            .price(4900)
            .build();

    protected FoodIntegrationTest.FoodDto food3 = FoodIntegrationTest.FoodDto.builder()
            .id(null)
            .name("쉐이크")
            .price(5900)
            .build();

    protected FoodIntegrationTest.FoodDto food4 = FoodIntegrationTest.FoodDto.builder()
            .id(null)
            .name("스트로베리베리")
            .price(11400)
            .build();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
}
