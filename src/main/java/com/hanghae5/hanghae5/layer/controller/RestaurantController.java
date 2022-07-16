package com.hanghae5.hanghae5.layer.controller;

import com.hanghae5.hanghae5.config.validation.CostumValidator;
import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.AddMenuRequest;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateRestaurantRequest;
import com.hanghae5.hanghae5.layer.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private CostumValidator costumValidator;

    // List는 Validation이 되지 않는다.
    // 따라서 커스텀으로 Validation을 만들어서 주입해야한다.
    @InitBinder()
    void initStudentValidator(WebDataBinder binder) {
        binder.setValidator(costumValidator);
    }

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant/register")
    public ResponseEntity register(@RequestBody @Valid CreateRestaurantRequest createRestaurantRequest){
        Restaurant restaurant = restaurantService.addRestaurant(createRestaurantRequest);
        return new ResponseEntity(restaurant, HttpStatus.OK);
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
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity getMenus(
            @PathVariable Long restaurantId){
        List<Food> results = restaurantService.getMenus(restaurantId);
        return new ResponseEntity(results, HttpStatus.OK);
    }
}
