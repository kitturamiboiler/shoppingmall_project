package com.nhnacademy.shoppingmall.order.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Order {
    private int id;
    private LocalDateTime createdAt;
    private String userId;
    private int productId;
    private int discount;
    private int quantity;
    private double subtotal;
    private double tax;
    private double total;

    public Order() {}

    public Order(String userId, int productId, int quantity, double total, LocalDateTime createdAt) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.createdAt = createdAt;
    }
}