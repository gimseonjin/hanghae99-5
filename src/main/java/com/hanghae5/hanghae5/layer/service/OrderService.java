package com.hanghae5.hanghae5.layer.service;

import com.hanghae5.hanghae5.layer.infra.FoodRepository;
import com.hanghae5.hanghae5.layer.infra.OrderRepository;
import com.hanghae5.hanghae5.layer.infra.RestaurantRepository;
import com.hanghae5.hanghae5.layer.model.Food;
import com.hanghae5.hanghae5.layer.model.Ordel;
import com.hanghae5.hanghae5.layer.model.OrderQuantity;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.request.CreateOrderRequest;
import com.hanghae5.hanghae5.layer.model.dto.response.GetOrdersResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final FoodRepository foodRepository;

    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public GetOrdersResponse order(CreateOrderRequest createOrderRequest){
        // 주문 객체 생성
        Ordel order = Ordel.builder().orderQuantityList(new ArrayList<>()).build();

        // Request 주문 객체로 변환
        requestToOrder(createOrderRequest, order);

        // DB에 저장
        orderRepository.save(order);

        // 주문 객체 Response 변환
        GetOrdersResponse getOrdersResponse = new GetOrdersResponse(order);

        // 최소 주문 체크
        checkTotalPriceBiggerThanMinOrderPrice(getOrdersResponse, order.getRestaurant());

        // 결과 반환
        return getOrdersResponse;
    }

    @Transactional
    public List<GetOrdersResponse> getOrders(){
        // 모든 주문 가져오기
        List<Ordel> orders = orderRepository.findAll();

        // Response 객체로 변환 후 반환
        return orders.stream().map(order -> new GetOrdersResponse(order))
                .collect(Collectors.toList());
    }

    private void requestToOrder(CreateOrderRequest createOrderRequest, Ordel order){
        // 주문 생성 요청 DTO 값 중, Food 부분 매핑하기
        foodInCreateOrderRequestToOrder(createOrderRequest, order);

        // 레스토랑 객체 가져오기
        Restaurant restaurant = restaurantRepository.findById(createOrderRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        // 주문에 매핑
        order.setRestaurant(restaurant);
    }

    private void foodInCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest, Ordel order){
        createOrderRequest.getFoods().stream()
                .map(foods -> {
                    // 음식 객체를 주문량 객체로 변환
                    Food food = foodRepository.findById(foods.getId())
                            .orElseThrow(()->new RuntimeException("해당 음식을 찾을 수 없습니다."));

                    OrderQuantity orderQuantity = OrderQuantity.builder()
                            .food(food)
                            .quantity(foods.getQuantity())
                            .build();

                    orderQuantity.setFood(food);

                    return orderQuantity;
                }) // 주문량 객체를 주문 객체에 넣기
                .forEach(orderQuantity -> order.addQuantity(orderQuantity));
    }

    private void checkTotalPriceBiggerThanMinOrderPrice(GetOrdersResponse getOrdersResponse, Restaurant restaurant){
        // 배달비가 최소 주문 금액이 넘는 지 체크
        if(getOrdersResponse.getTotalPrice() < restaurant.getMinOrderPrice())
            throw new RuntimeException("배달비가 최소 금액을 넘어야합니다.");
    }
}
