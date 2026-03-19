package com.nhnacademy.shop.order.repository.impl;


import com.nhnacademy.shoppingmall.config.RootConfig;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringJUnitConfig(classes = {RootConfig.class})
@Transactional
public class OrderRepositoryImplTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private final String TEST_USER_ID = "order_test_user";
    private int testProductId;

    @BeforeEach
    void setUp() {
        User user = new User(TEST_USER_ID, "주문테스터", "1234", "19950505", User.Auth.ROLE_USER, 0, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user);

        Product product = new Product(0, 1, "테스트 상품", 10000, 100, "EAN001", 4.5, "VendorX", LocalDateTime.now());
        testProductId = productRepository.save(product);
    }
    @Test
    @DisplayName("주문 저장 및 단건 조회 테스트")
    void saveAndFindById_Success() {
        Order order = new Order(TEST_USER_ID, testProductId, 2, 200000.0, LocalDateTime.now());
        int generatedId = orderRepository.save(order);
        Optional<Order> foundOrder= orderRepository.findById(generatedId);
        assertTrue(foundOrder.isPresent());
        assertEquals(TEST_USER_ID, foundOrder.get().getUserId());
        assertEquals(testProductId, foundOrder.get().getProductId());
        assertEquals(2, foundOrder.get().getQuantity());
    }
    @Test
    @DisplayName("유저별 주문 내역 조회 카운트 테스트")
    void findAllByUserId_And_Count_check() {
        orderRepository.save(new Order(TEST_USER_ID, testProductId, 1, 10000.0, LocalDateTime.now()));
        orderRepository.save(new Order(TEST_USER_ID, testProductId, 4, 10000.0, LocalDateTime.now()));
        List<Order> orders = orderRepository.findAllByUserId(TEST_USER_ID, 0, 10);
        long count = orderRepository.countByUserId(TEST_USER_ID);
        assertEquals(2, orders.size());
        assertEquals(2L, count);
        assertTrue(orders.get(0).getId()> 0);
    }
    @Test
    @DisplayName("유저의 모든 주문 삭제 테스트")
    void deleteByUserId_Success() {
        orderRepository.save(new Order(TEST_USER_ID, testProductId, 1, 10000.0, LocalDateTime.now()));
        int deletedCount = orderRepository.deleteByUserId(TEST_USER_ID);
        long remainCount = orderRepository.countByUserId(TEST_USER_ID);
        assertTrue(deletedCount > 0);
        assertEquals(0L, remainCount);
    }
}
