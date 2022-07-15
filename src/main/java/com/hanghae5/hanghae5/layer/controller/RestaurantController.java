package com.hanghae5.hanghae5.layer.controller;

import com.hanghae5.hanghae5.layer.model.dto.request.CreateRestaurantRequest;
import com.hanghae5.hanghae5.layer.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CreateRestaurantRequest createRestaurantRequest){
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
