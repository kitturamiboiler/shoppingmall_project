package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.repository.OrderItemRepository;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final PointHistoryService pointHistoryService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, UserService userService, PointHistoryService pointHistoryService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.pointHistoryService = pointHistoryService;
    }

    @Override
    public void createOrder(Order order, List<OrderItem> items) {
        double calculatedTotal = 0;
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId());

            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("재고가 부족합니다: " + product.getTitle());
            }

            item.setPrice(product.getPrice());
            calculatedTotal += (product.getPrice() * item.getQuantity());

            productRepository.updateStock(product.getId(), item.getQuantity());
        }
        order.setTotal(calculatedTotal);

        int totalAmount = (int) Math.round(order.getTotal());
        userService.updateUserPoint(order.getUserId(), -totalAmount, "상품 주문 결제");
        pointHistoryService.recordHistory(order.getUserId(), -totalAmount, "주문 완료");

        int orderId = orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrderId(orderId);
            orderItemRepository.save(item);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}