package com.nhnacademy.shop.order.service.impl;


import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    private final String TEST_USER = "order_service_user";

    @Test
    @DisplayName("주문")
    void Order_Success() {
        Order order = new Order(TEST_USER, 100, 2, 5000.0, LocalDateTime.now());
        orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
    }
    @Test
    @DisplayName("유저별 주문 내역 페이징")
    void getOrderByUserId_Paging() {
        int page = 3;
        int pageSize = 5;
        long totalCount = 20L;
        when(orderRepository.countByUserId(TEST_USER)).thenReturn(totalCount);
        when(orderRepository.findAllByUserId(TEST_USER, 10, pageSize)).thenReturn(List.of(mock(Order.class)));
        Page<Order> result = orderService.getOrdersByUserId(TEST_USER, page, pageSize);
        assertNotNull(result);
        assertEquals(totalCount, result.getTotalCount());
        verify(orderRepository).findAllByUserId(TEST_USER, 10, pageSize);
    }
    @Test
    @DisplayName("전체 주문 조회")
    void getOrders_Pagination() {
        int page = 1;
        int pageSize =10;
        long totalCount = 50L;
        when(orderRepository.countAll()).thenReturn(totalCount);
        when(orderRepository.findAll(0, pageSize)).thenReturn(List.of(mock(Order.class)));
        Page<Order> result = orderService.getOrders(page, pageSize);
        assertEquals(totalCount, result.getTotalCount());
        verify(orderRepository).findAll(0, pageSize);
    }
    @Test
    @DisplayName("주문 삭제 테스트")
    void deleteOrder_Success() {
        when(orderRepository.deleteByUserId(TEST_USER)).thenReturn(1);
        assertDoesNotThrow(() -> orderService.deleteOrder(TEST_USER));
        verify(orderRepository).deleteByUserId(TEST_USER);
    }
    @Test
    @DisplayName("주문 삭제 실패")
    void deleteOrder_Failed() {
        when(orderRepository.deleteByUserId(TEST_USER)).thenReturn(0);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.deleteOrder(TEST_USER));
        assertEquals("Not Found Order", exception.getMessage());
    }
}
