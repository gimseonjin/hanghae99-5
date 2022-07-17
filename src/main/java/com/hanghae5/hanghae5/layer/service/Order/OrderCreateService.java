package com.hanghae5.hanghae5.layer.service.Order;

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
public class OrderCreateService {

    private final OrderRepository orderRepository;

    private final FoodRepository foodRepository;

    private final RestaurantRepository restaurantRepository;

    public OrderCreateService(OrderRepository orderRepository, FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public GetOrdersResponse order(CreateOrderRequest createOrderRequest){
        // Request 주문 객체로 변환
        Ordel order = createOrderRequestToOrder(createOrderRequest);

        // 주문 최대 수량 체크
        checkTotalQuantity(order);

        // 주문 최소 금액이 넘는 지 체크
        checkTotalPriceBiggerThanMinOrderPrice(order);

        // 데이터 베이스에 저장
        orderRepository.save(order);

        // Response Entity에 담아서 반환
        return new GetOrdersResponse(order);
    }

    // ======================== Dto & Model 변환 부분 ========================
    private Ordel createOrderRequestToOrder(CreateOrderRequest createOrderRequest){
        // 주문 객체 생성
        Ordel order = Ordel.builder().orderQuantityList(new ArrayList<>()).build();

        // 주문 생성 요청 DTO 값 중, Food 부분 매핑하기
        List<OrderQuantity> orderQuantityList = foodInCreateOrderRequestToOrder(createOrderRequest);

        // 레스토랑 객체 가져오기
        Restaurant restaurant = restaurantRepository.findById(createOrderRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("해당 레스토랑을 찾을 수 없습니다."));

        // 주문에 매핑
        order.setRestaurant(restaurant);
        order.setOrderQuantityList(orderQuantityList);

        return order;
    }

    private List<OrderQuantity> foodInCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest){
        return createOrderRequest.getFoods().stream()
                .map(foods -> foodToOrderQuantity(foods)) // 주문량 객체를 주문 객체에 넣기
                .collect(Collectors.toList());
    }

    private OrderQuantity foodToOrderQuantity(CreateOrderRequest.FoodRequest foods){
        Food food = foodRepository.findById(foods.getId())
                .orElseThrow(()->new RuntimeException("해당 음식을 찾을 수 없습니다."));

        return OrderQuantity.builder()
                .food(food).quantity(foods.getQuantity()).build();
    }

    // ======================== Service 예외 처리 부분 ========================
    private void checkTotalPriceBiggerThanMinOrderPrice(Ordel order){
        // 배달비가 최소 주문 금액이 넘는 지 체크, 이때 배달비는 포함되선 안된다.
        if(order.getTotalPrice() < order.getRestaurant().getMinOrderPrice())
            throw new RuntimeException("배달비가 최소 금액을 넘어야합니다.");
    }

    private void checkTotalQuantity(Ordel order){
        // 주문 시, 전체 음식 주문량 계산
        int totalQuantity = order.getOrderQuantityList().stream()
                .map(orderQuantity -> orderQuantity.getQuantity()).reduce(0, (x, y)-> x + y);

        // 일회 음식 주문 최대 수량 초과여부 판단.
        if(totalQuantity > 100)
            throw new RuntimeException("일회 음식 주문 최대 수량을 초과하였습니다 : 최대 100개");
    }
}
