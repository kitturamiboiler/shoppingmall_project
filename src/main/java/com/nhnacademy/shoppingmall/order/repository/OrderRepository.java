package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    int save(Order order);
    Optional<Order> findById(int id);
    List<Order> findAllByUserId(String userId);
}