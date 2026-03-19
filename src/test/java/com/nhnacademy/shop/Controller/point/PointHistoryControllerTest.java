//package com.nhnacademy.shop.Controller.point;
//
//import com.nhnacademy.shoppingmall.common.page.Page;
//import com.nhnacademy.shoppingmall.controller.mypage.PointHistoryController;
//import com.nhnacademy.shoppingmall.point.domain.PointHistory;
//import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PointHistoryControllerTest {
//
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private HttpSession session;
//    @Mock private PointHistoryService pointHistoryService;
//    @Mock private User mockUser;
//    @Mock private Page<PointHistory> mockPage;
//
//    private PointHistoryController pointHistoryController;
//
//    @BeforeEach
//    void setUp() {
//        pointHistoryController = new PointHistoryController();
//        ReflectionTestUtils.setField(pointHistoryController, "pointHistoryService", pointHistoryService);
//
//        lenient().when(request.getSession(false)).thenReturn(session);
//        lenient().when(session.getAttribute("user")).thenReturn(mockUser);
//        lenient().when(mockUser.getUserId()).thenReturn("tester");
//    }
//
//    @Test
//    @DisplayName("포인트 이력 조회 성공 - 2페이지 요청 및 토탈 페이지 계산 확인")
//    void pointHistory_Success() {
//        when(request.getParameter("page")).thenReturn("2");
//        when(pointHistoryService.getPointHistoryPage("tester", 2, 10)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(25L);
//        String viewName = pointHistoryController.execute(request, response);
//        assertEquals("shop/mypage/point_history", viewName);
//        verify(request).setAttribute("pointHistoryPage", mockPage);
//        verify(request).setAttribute("currentPage", 2);
//        verify(request).setAttribute("totalPages", 3L);
//    }
//
//    @Test
//    @DisplayName("포인트 이력 조회 - 페이지 파라미터 없을 때 1페이지로 기본 동작")
//    void pointHistory_DefaultPage_Success() {
//        when(request.getParameter("page")).thenReturn(null);
//        when(pointHistoryService.getPointHistoryPage("tester", 1, 10)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(5L);
//        pointHistoryController.execute(request, response);
//        verify(request).setAttribute("currentPage", 1);
//        verify(pointHistoryService).getPointHistoryPage("tester", 1, 10);
//    }
//}
