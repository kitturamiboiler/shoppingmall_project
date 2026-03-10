package com.nhnacademy.shoppingmall.point.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;

public interface PointHistoryService {
    Page<PointHistory> getPointHistoryPage(String userId, int page, int pageSize);
    void recordHistory(String userId, int amount, String reason);
}