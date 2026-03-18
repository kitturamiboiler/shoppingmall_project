package com.nhnacademy.shoppingmall.point.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    @Autowired
    public PointHistoryServiceImpl(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointHistory> getPointHistoryPage(String userId, int page, int pageSize) {
        int currentPage = Math.max(page, 1);
        int offset = (currentPage - 1) * pageSize;
        List<PointHistory> content = pointHistoryRepository.findAllByUserId(userId, offset, pageSize);
        long totalCount = pointHistoryRepository.countByUserId(userId);
        return new Page<>(content, totalCount);
    }

    @Override
    @Transactional
    public void recordHistory(String userId, int amount, String reason) {
        PointHistory history = new PointHistory(null, userId, amount, reason, LocalDateTime.now());
        pointHistoryRepository.save(history);
    }

    @Override
    @Transactional
    public void deleteHistory(String userId) {
        pointHistoryRepository.deleteByUserId(userId);
    }
}