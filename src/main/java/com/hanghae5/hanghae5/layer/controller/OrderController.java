package com.hanghae5.hanghae5.layer.controller;

import com.hanghae5.hanghae5.layer.model.dto.request.CreateOrderRequest;
import com.hanghae5.hanghae5.layer.model.dto.response.GetOrdersResponse;
import com.hanghae5.hanghae5.layer.service.Order.OrderCreateService;
import com.hanghae5.hanghae5.layer.service.Order.OrderReadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderCreateService orderCreateService;
    private final OrderReadService orderReadService;

    public OrderController(OrderCreateService orderCreateService, OrderReadService orderReadService) {
        this.orderCreateService = orderCreateService;
        this.orderReadService = orderReadService;
    }

    @PostMapping("/order/request")
    public ResponseEntity order(@RequestBody CreateOrderRequest createOrderRequest){
        GetOrdersResponse result = orderCreateService.order(createOrderRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(){
        List<GetOrdersResponse> results = orderReadService.getOrders();
        return new ResponseEntity(results, HttpStatus.OK);
    }
}
