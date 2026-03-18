package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrdersByUserId(String userId, int page, int pageSize) {
        int currentPage = Math.max(page, 1);
        int offset = (currentPage - 1) * pageSize;
        List<Order> orderList = orderRepository.findAllByUserId(userId, offset, pageSize);
        long totalCount = orderRepository.countByUserId(userId);
        return new Page<>(orderList, totalCount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrders(int page, int pageSize) {
        int currentPage = Math.max(page, 1);
        int offset = (currentPage - 1) * pageSize;
        List<Order> orderList = orderRepository.findAll(offset, pageSize);
        long totalCount = orderRepository.countAll();
        return new Page<>(orderList, totalCount);
    }

    @Override
    @Transactional
    public void deleteOrder(String userId) {
        if(orderRepository.deleteByUserId(userId) == 0){
            throw new RuntimeException("Not Found Order");
        }
    }
}