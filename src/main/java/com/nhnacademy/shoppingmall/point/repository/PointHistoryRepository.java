package com.nhnacademy.shoppingmall.point.repository;

import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import java.util.List;

public interface PointHistoryRepository {
    void save(PointHistory pointHistory);
    List<PointHistory> findAllByUserId(String userId, int offset, int limit);
    long countByUserId(String userId);
}
