package com.learn.demo.controller;

import com.learn.demo.dto.CreateOrderRequest;
import com.learn.demo.dto.OrderResponse;
import com.learn.demo.mapper.OrderMapper;
import com.learn.demo.model.OrderEntity;
import com.learn.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderEntity createdOrder = orderService.createOrder(request.getUserId(), request.getTotal());
        return new ResponseEntity<>(orderMapper.toResponse(createdOrder), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderEntity order = orderService.getOrderById(id);
        return new ResponseEntity<>(orderMapper.toResponse(order), HttpStatus.OK);
    }

    @GetMapping
    public org.springframework.data.domain.Page<OrderResponse> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return orderService.getAllOrders(page, size, sortBy, sortDir)
                .map(orderMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
