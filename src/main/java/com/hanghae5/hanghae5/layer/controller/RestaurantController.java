package com.hanghae5.hanghae5.layer.controller;

import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.AddMenuRequest;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateRestaurantRequest;
import com.hanghae5.hanghae5.layer.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant/register")
    public ResponseEntity register(@RequestBody @Valid CreateRestaurantRequest createRestaurantRequest){
        restaurantService.addRestaurant(createRestaurantRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/restaurants")
    public ResponseEntity getRestaurant(){
        List<Restaurant> results = restaurantService.getAllRestaurant();
        return new ResponseEntity(results, HttpStatus.OK);
    }

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public ResponseEntity foodRegister(
            @RequestBody @Valid List<AddMenuRequest> addMenuRequestList, @PathVariable Long restaurantId){
        restaurantService.addMenus(restaurantId, addMenuRequestList);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity getMenus(
            @PathVariable Long restaurantId){
        List<Food> results = restaurantService.getMenus(restaurantId);
        return new ResponseEntity(results, HttpStatus.CREATED);
    }
}
