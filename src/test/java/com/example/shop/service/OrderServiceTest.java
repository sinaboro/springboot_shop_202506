package com.example.shop.service;

import com.example.shop.dto.OrderDto;
import com.example.shop.entity.Order;
import com.example.shop.repository.OrderItemRepository;
import com.example.shop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //DB저정하지마세요
@Slf4j
@WithMockUser(username = "user1@user.com", roles = "ADMIN")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Test
    public void test() {

        String email = "user1@user.com";

        OrderDto orderDto = new OrderDto();

        orderDto.setCount(1);
        orderDto.setItemId(1L);

        Long order = orderService.order(orderDto, email);

        log.info("---------order---------- : {}", order);

        Order savedOrder = orderRepository.findById(order)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        log.info("---------savedOrder---------- : {}", savedOrder);

        savedOrder.getOrderItems().forEach(orderItem -> {log.info("OrderItem: {}", orderItem);});
    }
}