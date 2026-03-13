package com.nhnacadmey.shop.order;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderRepositoryImplTest {
    OrderRepository orderRepository = new OrderRepositoryImpl();

    Order testOrder;
    LocalDateTime testTime;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testTime = LocalDateTime.now();
        testOrder = new Order("testUserId", 12345678, 50, 100, testTime);
        orderRepository.save(testOrder);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }
}
