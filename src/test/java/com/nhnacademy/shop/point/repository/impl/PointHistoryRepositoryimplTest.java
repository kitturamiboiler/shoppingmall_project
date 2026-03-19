package com.nhnacademy.shop.point.repository.impl;


import com.nhnacademy.shoppingmall.config.RootConfig;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringJUnitConfig(classes = {RootConfig.class})
@Transactional
public class PointHistoryRepositoryimplTest {
    @Autowired
    private PointHistoryRepository pointHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        User testUser = new User(
                TEST_USER,
                "테스터",
                "12345",
                "19900101",
                User.Auth.ROLE_USER,
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userRepository.save(testUser);
    }
    private final String TEST_USER = "test_user_spring";


    @Test
    @DisplayName("포인트 이력 저장, 카운트 테스트")
    void saveAndCount_Success(){
        PointHistory pointHistory = new PointHistory(
                0L, TEST_USER, 1000, "가입 축하 지원금", LocalDateTime.now()
        );
        pointHistoryRepository.save(pointHistory);
        long count = pointHistoryRepository.countByUserId(TEST_USER);
        assertEquals(1L, count, "해당 유저의 포인트 내역이 1건 존재 해야 합니다.");
    }

    @Test
    @DisplayName("유저별 포인트 이력 조회 페이징 테스트")
    void findByIserId_Pagination_And_Order_Check() throws InterruptedException{
        pointHistoryRepository.save(new PointHistory(
                0L, TEST_USER, 100, "first", LocalDateTime.now()
        ));
        Thread.sleep(100);
        pointHistoryRepository.save(new PointHistory(
                0L, TEST_USER, -50, "second", LocalDateTime.now()
        ));
        List<PointHistory> pointHistoryList = pointHistoryRepository.findAllByUserId(TEST_USER, 0, 10);
        assertNotNull(pointHistoryList);
        assertTrue(pointHistoryList.size() >= 2);
        assertEquals(100, pointHistoryList.getFirst().getAmount(), "최신 내역이 가장 먼저 조회되어야 함");
    }
    @Test
    @DisplayName("특정 유저의 모든 포잍느 이력 삭제 테스트")
    void deleteByUserId_Success() {
        pointHistoryRepository.save(new PointHistory(0L, TEST_USER, 500, "delete Test", LocalDateTime.now()));
        int deleteCount = pointHistoryRepository.deleteByUserId(TEST_USER);
        long remainCount = pointHistoryRepository.countByUserId(TEST_USER);
        assertTrue(deleteCount >0);
        assertEquals(0, remainCount, "삭제된 유저의 내역은 0건이어야 함");
    }
}
