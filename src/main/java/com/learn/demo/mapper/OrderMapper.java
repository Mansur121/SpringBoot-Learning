package com.learn.demo.mapper;

import com.learn.demo.dto.OrderResponse;
import com.learn.demo.model.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(OrderEntity order) {
        if (order == null) {
            return null;
        }
        return new OrderResponse(
                order.getId(),
                order.getTotal(),
                order.user != null ? order.user.getId() : 0,
                order.user != null ? order.user.getName() : null);
    }
}
