package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import java.util.List;

public interface OrderItemRepository {
    int save(OrderItem orderItem);
    List<OrderItem> findAllByOrderId(int orderId);
}