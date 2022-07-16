package com.hanghae5.hanghae5.layer.controller;

import com.hanghae5.hanghae5.layer.model.dto.request.CreateOrderRequest;
import com.hanghae5.hanghae5.layer.model.dto.response.GetOrdersResponse;
import com.hanghae5.hanghae5.layer.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity order(@RequestBody CreateOrderRequest createOrderRequest){
        GetOrdersResponse result = orderService.order(createOrderRequest);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(){
        List<GetOrdersResponse> results = orderService.getOrders();
        return new ResponseEntity(results, HttpStatus.CREATED);
    }
}
