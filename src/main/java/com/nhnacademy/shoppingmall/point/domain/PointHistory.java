package com.nhnacademy.shoppingmall.point.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointHistory {
    private final Long id;
    private final String userId;
    private final int amount;
    private final String reason;
    private final LocalDateTime createdAt;

    public PointHistory(Long id, String userId, int amount, String reason, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = createdAt;
    }
}
