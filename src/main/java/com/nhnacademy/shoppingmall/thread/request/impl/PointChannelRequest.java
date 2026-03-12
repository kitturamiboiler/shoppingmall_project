package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final String userId;
    private final int totalAmount;
    private static final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private static final PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
    private static final String POINT_REASON = "상품 구매 적립(10%)";

    public PointChannelRequest(String userId, int totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }
    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();

        try {
            log.debug("포인트 적립 - 사용자: {}, 주문금액: {}", userId, totalAmount);
            int pointToAccumulate = (int) (totalAmount * 0.1);
            userService.updateUserPoint(userId, pointToAccumulate);
            pointHistoryService.recordHistory(userId, pointToAccumulate, POINT_REASON);
            log.debug("포인트 적립 완료- 사용자: {}, 적립포인트: {}", userId, pointToAccumulate);
        } catch (Exception e) {
            log.error("포인트 적립 중 오류 발생 - 사용자: {}, 금액: {}, 사유: {}",
                    userId, totalAmount, e.getMessage());
        } finally {
            DbConnectionThreadLocal.reset();
            log.debug("pointChannel 커넥션 반납 완료");
        }
    }
}
