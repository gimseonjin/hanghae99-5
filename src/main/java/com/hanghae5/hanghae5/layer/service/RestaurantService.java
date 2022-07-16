package com.hanghae5.hanghae5.layer.service;

import com.hanghae5.hanghae5.layer.infra.RestaurantRepository;
import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.AddMenuRequest;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateRestaurantRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public Restaurant addRestaurant(CreateRestaurantRequest createRestaurantRequest){
        Restaurant restaurant = createRestaurantRequest.toRestaurant();
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurant(){
        return restaurantRepository.findAll();
    }

    @Transactional
    public void addMenus(Long restaurantId,  List<AddMenuRequest> addMenuRequests){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        addMenuRequests.stream()
                .map(AddMenuRequest::toFood)
                .forEach(menu -> restaurant.addMenu(menu));

        restaurantRepository.save(restaurant);
    }

    @Transactional
    public List<Food> getMenus(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        return restaurant.getMenu();
    }

}
