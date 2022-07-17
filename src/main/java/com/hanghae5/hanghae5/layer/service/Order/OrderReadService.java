package com.hanghae5.hanghae5.layer.service.Order;

import com.hanghae5.hanghae5.layer.infra.OrderRepository;
import com.hanghae5.hanghae5.layer.model.Ordel;
import com.hanghae5.hanghae5.layer.model.OrderQuantity;
import com.hanghae5.hanghae5.layer.model.Restaurant;
import com.hanghae5.hanghae5.layer.model.dto.response.GetOrdersResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderReadService {

    private final OrderRepository orderRepository;

    public OrderReadService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<GetOrdersResponse> getOrders(){
        // 모든 주문 가져오기
        List<Ordel> orders = orderRepository.findAll();

        // Response 객체로 변환 후 반환
        return orders.stream().map(order -> new GetOrdersResponse(order))
                .collect(Collectors.toList());
    }
}
