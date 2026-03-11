package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import java.util.List;

public interface OrderService {
    void createOrder(Order order, List<OrderItem> items);
    List<Order> getOrdersByUserId(String userId);
}