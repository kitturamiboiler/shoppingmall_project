package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<Order> getOrdersByUserId(String userId, int page, int pageSize) {
        int currentPage = Math.max(page, 1);
        int offset = (currentPage - 1) * pageSize;
        List<Order> orderList = orderRepository.findAllByUserId(userId, offset, pageSize);
        long totalCount = orderRepository.countByUserId(userId);
        return new Page<>(orderList, totalCount);
    }
}