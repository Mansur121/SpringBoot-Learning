package com.learn.demo.service;

import com.learn.demo.exception.OrderNotFoundException;
import com.learn.demo.exception.UserNotFoundException;
import com.learn.demo.model.OrderEntity;
import com.learn.demo.model.UserEntity;
import com.learn.demo.repository.OrderRepository;
import com.learn.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderEntity createOrder(int userId, BigDecimal total) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        OrderEntity orderEntity = new OrderEntity(user, total);
        return orderRepository.save(orderEntity);
    }

    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public Page<OrderEntity> getAllOrders(int page, int size, String sortBy,
            String sortDirection) {
        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size,
                sort);
        return orderRepository.findAll(pageable);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}
