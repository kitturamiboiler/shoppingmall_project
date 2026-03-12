package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;

public interface OrderService {
    void createOrder(Order order);
    Page<Order> getOrdersByUserId(String userId, int page, int pageSize);
}