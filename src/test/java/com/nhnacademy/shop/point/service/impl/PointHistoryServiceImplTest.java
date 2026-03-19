package com.nhnacademy.shop.point.service.impl;


import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceImplTest {
    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @InjectMocks
    private PointHistoryServiceImpl pointHistoryService;
    private final String TEST_USER = "test_user";

    @Test
    @DisplayName("포인트 이력 페이지 조회-> Offset 계산 및 데이터 검증")
    void getPointHistoryPage_Check() {
        int page =2;
        int pageSize= 10;
        long totalCount = 25L;
        PointHistory pointHistory = new PointHistory(1L, TEST_USER, 100, "test", LocalDateTime.now());
        when(pointHistoryRepository.countByUserId(TEST_USER)).thenReturn(totalCount);
        when(pointHistoryRepository.findAllByUserId(TEST_USER, 10, pageSize)).thenReturn(List.of(pointHistory));
        Page<PointHistory> result = pointHistoryService.getPointHistoryPage(TEST_USER, page, pageSize);
        assertNotNull(result);
        assertEquals(totalCount, result.getTotalCount());
        assertEquals(1, result.getContent().size());
        verify(pointHistoryRepository).findAllByUserId(TEST_USER, 10, pageSize);
        verify(pointHistoryRepository).countByUserId(TEST_USER);
    }
    @Test
    @DisplayName("포인트 이력 기록 -> 레포지토리 저장 확인")
    void recordHistory_Save() {
        pointHistoryService.recordHistory(TEST_USER, 500, "order mouse");
        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }
    @Test
    @DisplayName("포인트 이력 전체 삭제")
    void deleteHistory() {
        pointHistoryService.deleteHistory(TEST_USER);
        verify(pointHistoryRepository).deleteByUserId(TEST_USER);
    }

}
